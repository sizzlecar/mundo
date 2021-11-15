package com.bluslee.mundo.core.process.base;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 流程引擎基类，提供流程流程相关服务，流程通过有向图实现，解析表达式通过执行器实现.
 *
 * @author carl.che
 * @see Execute
 * @see BaseDirectedValueGraph
 * @see ProcessEngine
 */
public abstract class BaseProcessEngine<N extends BaseProcessNode, V> implements ProcessEngine<N> {

    private final Execute execute;

    private final BaseDirectedValueGraph<N, V> baseDirectedValueGraph;

    private final String id;

    private final Integer version;

    public BaseProcessEngine(final String id, final Execute execute, final BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
        this.baseDirectedValueGraph = baseDirectedValueGraph;
        this.execute = execute;
        this.id = id;
        this.version = 0;
    }

    /**
     * 通过流程元素id，获取对应的节点对象.
     *
     * @param processNodeId id
     * @return 对应的节点对象
     */
    @Override
    public N getProcessNode(final String processNodeId) {
        return baseDirectedValueGraph.nodes().stream().filter(node -> node.getId().equals(processNodeId)).findFirst().orElse(null);
    }

    /**
     * 根据当前节点，以及业务参数计算出下一个节点，或者多个节点.
     *
     * @param currentNode  当前节点
     * @param parameterMap 参数map
     * @return 节点包装类，下一个节点可能是一个，也肯能是并行的多个节点
     */
    @Override
    public ProcessNodeWrap<N> getNextProcessNode(final N currentNode, final Map<String, Object> parameterMap) {
        boolean contains = baseDirectedValueGraph.nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        return currentNode.next(baseDirectedValueGraph, parameterMap, execute);
    }

    /**
     * 根据当前节点id，以及业务参数计算出下一个节点，或者多个节点.
     *
     * @param currentNodeId 当前节点id
     * @param parameterMap  参数map
     * @return 节点包装类，下一个节点可能是一个，也肯能是并行的多个节点
     */
    @Override
    public ProcessNodeWrap<N> getNextProcessNode(final String currentNodeId, final Map<String, Object> parameterMap) {
        return getNextProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    /**
     * 根据当前节点，以及业务参数，预测后续所有可能出现的节点.
     *
     * @param currentNode  当前节点
     * @param parameterMap 参数map
     * @return 后续所有可能出现的节点
     */
    @Override
    public Set<N> forecastProcessNode(final N currentNode, final Map<String, Object> parameterMap) {
        boolean contains = baseDirectedValueGraph.nodes().contains(currentNode);
        if (!contains) {
            //currentNode 在当前流程中不存在
            throw new MundoException("currentNode not in process");
        }
        Set<N> forecastProcessNodeSet = new LinkedHashSet<>();
        forecastProcessNode(currentNode, parameterMap, forecastProcessNodeSet);
        return forecastProcessNodeSet;
    }

    /**
     * 根据当前节点id，以及业务参数，预测后续所有可能出现的节点.
     *
     * @param currentNodeId 当前节点id
     * @param parameterMap  参数map
     * @return 后续所有可能出现的节点
     */
    @Override
    public Set<N> forecastProcessNode(final String currentNodeId, final Map<String, Object> parameterMap) {
        return forecastProcessNode(getProcessNode(currentNodeId), parameterMap);
    }

    private void forecastProcessNode(final N currentNode, final Map<String, Object> parameterMap, final Set<N> forecastProcessNodeSet) {
        if (forecastProcessNodeSet.contains(currentNode)) {
            return;
        }
        forecastProcessNodeSet.add(currentNode);
        if (currentNode instanceof EndNode) {
            return;
        }
        ProcessNodeWrap<N> next = currentNode.next(baseDirectedValueGraph, parameterMap, execute);
        if (!next.parallel()) {
            N nextNode = next.get();
            forecastProcessNode(nextNode, parameterMap, forecastProcessNodeSet);
        } else {
            Set<N> parallelNodes = next.getParallelNodes();
            parallelNodes.forEach(nextNode -> forecastProcessNode(nextNode, parameterMap, forecastProcessNodeSet));
        }
    }

    /**
     * 获取当前流程id.
     *
     * @return 当前流程id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * 获取当前流程版本.
     *
     * @return 当前流程版本
     */
    @Override
    public Integer getVersion() {
        return version;
    }

    /**
     * 根据id，version判断与另一个对象是否相等.
     *
     * @param o 判断的对象
     * @return true 相等，false 不相等
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseProcessEngine<?, ?> that = (BaseProcessEngine<?, ?>) o;
        return version.equals(that.version) && id.equals(that.id);
    }

    /**
     * 使用id，version生成hashcode.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
