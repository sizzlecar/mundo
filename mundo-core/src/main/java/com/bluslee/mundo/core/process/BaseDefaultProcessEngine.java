package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.graph.BaseDirectedValueGraph;
import com.google.common.graph.EndpointPair;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDefaultProcessEngine 流程引擎基类，本质是一个提供了BaseProcessEngine服务的有向图
 */
public abstract class BaseDefaultProcessEngine<N extends BaseProcessNode, V> extends BaseDirectedValueGraph<N, V> implements BaseProcessEngine<N> {


    @Override
    public N getProcessNode(String processNodeId) {
        return super.nodes().stream().filter(node -> node.getId().equals(processNodeId)).findFirst().orElse(null);
    }

    @Override
    public N getNextProcessNode(N currentNode, Map<String, Object> parameterMap) {
        Optional<N> currentNodeOpt = super.nodes().stream().filter(node -> node.getId().equals(currentNode.getId())).findFirst();
        if (!currentNodeOpt.isPresent()) {
            //currentNode 在当前流程中不存在
            return null;
        }
        N processNode = currentNodeOpt.get();
        final Set<N> successors = super.successors(processNode);
        if (successors == null || successors.size() < 1) {
            //没有下一个节点
            return null;
        }
        List<N> singleList = new ArrayList<>(successors);
        N nextNode = singleList.get(0);
        if (nextNode instanceof BaseActivity) {
            //下一个节点是活动节点直接返回
            return nextNode;
        } else if (nextNode instanceof BaseExclusiveGateway) {
            //下一个节点是排他网关节点，需要计算边的值
            //排他网关作为source的边
            Set<EndpointPair<N>> outgoingEdges = super.outgoingEdges(nextNode);
            Optional<EndpointPair<N>> matchPair = outgoingEdges.stream().filter(pair -> {
                Optional<V> edgeValueOpt = super.edgeValue(pair);
                if (!edgeValueOpt.isPresent()) {
                    throw new MundoException("edge val is null");
                }
                V val = edgeValueOpt.get();
                //解析表达式，根据map进行计算 todo
                boolean res = true;
                return res;
            }).findFirst();
            if (!matchPair.isPresent()) {
                throw new MundoException("no match next node");
            }
            return matchPair.get().target();
        }
        return null;
    }

    @Override
    public N getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public List<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap) {
        Optional<N> currentNodeOpt = super.nodes().stream().filter(node -> node.getId().equals(currentNode.getId())).findFirst();
        if (!currentNodeOpt.isPresent()) {
            //currentNode 在当前流程中不存在
            return null;
        }
        List<N> forecastProcessNodeList = new ArrayList<>();
        N processNode = currentNodeOpt.get();
        foreachProcess(processNode, super.outgoingEdges(processNode), forecastProcessNodeList, parameterMap);
        return forecastProcessNodeList;
    }

    @Override
    public List<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    /**
     * 根据parameterMap遍历流程，返回经过的路径
     *
     * @param processNode             当前节点
     * @param outgoing                当前节点 outgoing edges
     * @param forecastProcessNodeList 经过的节点
     * @param parameterMap            业务参数map
     */
    private void foreachProcess(N processNode, Set<EndpointPair<N>> outgoing, List<N> forecastProcessNodeList, Map<String, Object> parameterMap) {
        if (outgoing == null || outgoing.size() < 1) {
            return;
        }
        outgoing.forEach(pair -> {
            N target = pair.target();
            N nextNode = null;
            if (target instanceof BaseActivity) {
                forecastProcessNodeList.add(target);
                nextNode = target;
            } else if (target instanceof BaseExclusiveGateway) {
                Set<EndpointPair<N>> targetOutgoingEdges = super.outgoingEdges(target);
                Optional<EndpointPair<N>> matchPairOpt = targetOutgoingEdges.stream()
                        .filter(outgoingPair -> {
                            Optional<V> conditionExpressOpt = super.edgeValue(outgoingPair);
                            if (!conditionExpressOpt.isPresent()) {
                                throw new MundoException("edge val is null");
                            }
                            V conditionExpress = conditionExpressOpt.get();
                            //解析表达式，根据map进行计算 todo
                            boolean res = true;
                            return res;
                        }).findFirst();
                if(matchPairOpt.isPresent()){
                    nextNode = matchPairOpt.get().target();
                    forecastProcessNodeList.add(nextNode);
                }
            }else {
                throw new MundoException("未知的类型");
            }
            foreachProcess(nextNode, super.outgoingEdges(nextNode), forecastProcessNodeList, parameterMap);
        });
    }
}
