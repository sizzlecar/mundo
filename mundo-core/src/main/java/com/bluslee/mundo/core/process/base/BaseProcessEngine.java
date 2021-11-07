package com.bluslee.mundo.core.process.base;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDefaultProcessEngine 流程引擎基类，本质是一个提供了BaseProcessEngine服务的有向图
 */
public abstract class BaseProcessEngine<N extends BaseProcessNode, V> implements ProcessEngine<N> {

    private Execute execute;
    private BaseDirectedValueGraph<N, V> baseDirectedValueGraph;
    private final String id;
    private int version;
    public BaseProcessEngine(String id, Execute execute, BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
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
    public Set<N> forecastProcessNode(N currentNode, Map<String, Object> parameterMap) {
        boolean contains = baseDirectedValueGraph.nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        Set<N> forecastProcessNodeSet = new LinkedHashSet<>();
        forecastProcessNode(currentNode, parameterMap, forecastProcessNodeSet);
        return forecastProcessNodeSet;
    }

    @Override
    public Set<N> forecastProcessNode(String currentNodeId, Map<String, Object> parameterMap) {
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

    void setExecute(Execute execute) {
        this.execute = execute;
    }

    void setBaseDirectedValueGraph(BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
        this.baseDirectedValueGraph = baseDirectedValueGraph;
    }

    private void forecastProcessNode(N currentNode, Map<String, Object> parameterMap, Set<N> forecastProcessNodeSet) {
        if (currentNode instanceof EndNode || forecastProcessNodeSet.contains(currentNode)) {
            return;
        }
        forecastProcessNodeSet.add(currentNode);
        ProcessNodeWrap<N> next = currentNode.next(baseDirectedValueGraph, parameterMap, execute);
        if (!next.parallel()) {
            N nextNode = next.get();
            forecastProcessNode(nextNode, parameterMap, forecastProcessNodeSet);
        } else {
            Set<N> parallelNodes = next.getParallelNodes();
            parallelNodes.forEach(nextNode -> forecastProcessNode(nextNode, parameterMap, forecastProcessNodeSet));
        }
    }
}
