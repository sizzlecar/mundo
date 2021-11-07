package com.bluslee.mundo.core.process.base;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDefaultProcessEngine 流程引擎基类，本质是一个提供了BaseProcessEngine服务的有向图
 */
public abstract class BaseDefaultProcessEngine<N extends BaseProcessNode, V> implements BaseProcessEngine<N> {

    private final Execute execute;
    private final BaseDirectedValueGraph<N, V> baseDirectedValueGraph;
    private final String id;
    private int version;
    public BaseDefaultProcessEngine(String id, Execute execute, BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
        this.baseDirectedValueGraph = baseDirectedValueGraph;
        this.execute = execute;
        this.id = id;
    }

    @Override
    public N getProcessNode(String processNodeId) {
        return baseDirectedValueGraph.nodes().stream().filter(node -> node.getId().equals(processNodeId)).findFirst().orElse(null);
    }

    @Override
    public ProcessNodeWrap<N> getNextProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = baseDirectedValueGraph.nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        return currentNode.next(baseDirectedValueGraph, parameterMap, execute);
    }

    @Override
    public ProcessNodeWrap<N> getNextProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public List<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = baseDirectedValueGraph.nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        List<N> forecastProcessNodeList = new ArrayList<>(baseDirectedValueGraph.nodes().size());
        forecastProcessNode(currentNode, parameterMap, forecastProcessNodeList);
        return forecastProcessNodeList;
    }

    @Override
    public List<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    private void forecastProcessNode(N currentNode, Map<String, Object> parameterMap, List<N> forecastProcessNodeList) {
        if (currentNode instanceof EndNode) {
            return;
        }
        ProcessNodeWrap<N> next = currentNode.next(baseDirectedValueGraph, parameterMap, execute);
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
