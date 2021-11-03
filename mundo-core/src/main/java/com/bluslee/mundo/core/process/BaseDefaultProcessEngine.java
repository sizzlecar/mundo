package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.graph.BaseDirectedValueGraph;
import com.bluslee.mundo.core.graph.Edge;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDefaultProcessEngine 流程引擎基类，本质是一个提供了BaseProcessEngine服务的有向图
 */
public abstract class BaseDefaultProcessEngine<N extends BaseProcessNode, V> extends BaseDirectedValueGraph<N, V> implements BaseProcessEngine<N> {

    private final Execute execute;

    public BaseDefaultProcessEngine(Execute execute) {
        this.execute = execute;
    }

    @Override
    public N getProcessNode(String processNodeId) {
        return nodes().stream().filter(node -> node.getId().equals(processNodeId)).findFirst().orElse(null);
    }

    @Override
    public N getNextProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        if(currentNode instanceof EndNode) {
            return currentNode;
        }
        final Set<N> successors = successors(currentNode);
        if (successors == null || successors.size() < 1) {
            throw new MundoException("nextNode not defined in process");
        }
        List<N> singleList = new ArrayList<>(successors);
        N nextNode = singleList.get(0);
        if (nextNode instanceof BaseActivity) {
            //下一个节点是活动节点直接返回
            return nextNode;
        } else if (nextNode instanceof BaseExclusiveGateway) {
            //下一个节点是排他网关节点，需要计算边的值
            //排他网关作为source的边
            Set<Edge<N>> outgoingEdges = outgoingEdges(nextNode);
            Optional<Edge<N>> matchPair = outgoingEdges.stream().filter(pair -> {
                Optional<V> edgeValueOpt = edgeValue(pair);
                if (!edgeValueOpt.isPresent()) {
                    throw new MundoException("edge val is null");
                }
                V expression = edgeValueOpt.get();
                //解析表达式，根据map进行计算
                return execute.executeExpression(expression, parameterMap);
            }).findFirst();
            if (!matchPair.isPresent()) {
                throw new MundoException("no match next node");
            }
            return matchPair.get().target();
        } else if (nextNode instanceof EndNode) {
            return nextNode;
        }
        return null;
    }

    @Override
    public N getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public List<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        List<N> forecastProcessNodeList = new ArrayList<>();
        foreachProcess(currentNode, outgoingEdges(currentNode), forecastProcessNodeList, parameterMap);
        return forecastProcessNodeList;
    }

    @Override
    public List<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public Set<N> getNextProcessNode(Set<N> currentNodeSet, Map<String, Object> parameterMap) {
        return null;
    }

    /**
     * 根据parameterMap遍历流程，返回经过的路径
     *
     * @param processNode             当前节点
     * @param outgoing                当前节点 outgoing edges
     * @param forecastProcessNodeList 经过的节点
     * @param parameterMap            业务参数map
     */
    private void foreachProcess(N processNode, Set<Edge<N>> outgoing, List<N> forecastProcessNodeList, Map<String, Object> parameterMap) {
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
                Set<Edge<N>> targetOutgoingEdges = super.outgoingEdges(target);
                Optional<Edge<N>> matchPairOpt = targetOutgoingEdges.stream()
                        .filter(outgoingPair -> {
                            Optional<V> conditionExpressOpt = edgeValue(outgoingPair);
                            if (!conditionExpressOpt.isPresent()) {
                                throw new MundoException("edge val is null");
                            }
                            V conditionExpress = conditionExpressOpt.get();
                            //解析表达式，根据map进行计算
                            return execute.executeExpression(conditionExpress, parameterMap);
                        }).findFirst();
                if (matchPairOpt.isPresent()) {
                    nextNode = matchPairOpt.get().target();
                    forecastProcessNodeList.add(nextNode);
                }
            } else {
                throw new MundoException("未知的类型");
            }
            foreachProcess(nextNode, super.outgoingEdges(nextNode), forecastProcessNodeList, parameterMap);
        });
    }
}
