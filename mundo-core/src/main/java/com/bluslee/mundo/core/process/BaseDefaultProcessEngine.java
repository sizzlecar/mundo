package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;

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
    public ProcessNodeWrap<N> getNextProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        return currentNode.next(this, parameterMap, execute);
    }

    @Override
    public ProcessNodeWrap<N> getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public List<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        List<N> forecastProcessNodeList = new ArrayList<>(nodes().size());
        forecastProcessNode(currentNode, parameterMap, forecastProcessNodeList);
        return forecastProcessNodeList;
    }

    @Override
    public List<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    private void forecastProcessNode(N currentNode, Map<String, Object> parameterMap, List<N> forecastProcessNodeList) {
        if (currentNode instanceof EndNode) {
            return;
        }
        ProcessNodeWrap<N> next = currentNode.next(this, parameterMap, execute);
        if (!next.parallel()) {
            N nextNode = next.get();
            forecastProcessNodeList.add(nextNode);
            if (nextNode instanceof EndNode) {
                return;
            }
            forecastProcessNode(nextNode, parameterMap, forecastProcessNodeList);
        } else {
            Set<N> parallelNodes = next.getParallelNodes();
            forecastProcessNodeList.addAll(parallelNodes);
            parallelNodes.forEach(nextNode -> forecastProcessNode(nextNode, parameterMap, forecastProcessNodeList));
        }
    }
}
