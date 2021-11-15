package com.bluslee.mundo.core.process.graph;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraphBuilder;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 有向图实现类.
 *
 * @author carl.che
 * @param <N> 图的节点类型
 * @param <V> 边的值的类型
 */
public class DirectedValueGraphImpl<N extends BaseProcessNode, V> extends BaseDirectedValueGraph<N, V> {

    private final com.google.common.graph.MutableValueGraph<N, V> directedValueGraph =
            ValueGraphBuilder.directed().allowsSelfLoops(true).build();

    /**
     * 如果该节点在途中不存在则向图添加一个节点.
     *
     * @param node 添加的节点
     * @return true 添加成功 false 添加失败
     */
    @Override
    public boolean addNode(final N node) {
        return directedValueGraph.addNode(node);
    }

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值<br/>
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值.
     *
     * @param nodeV nodeV
     * @param nodeU nodeU
     * @param value 边的值
     * @return 边的值
     */
    @Override
    public V putEdgeValue(final N nodeU, final N nodeV, final V value) {
        return directedValueGraph.putEdgeValue(nodeU, nodeV, value);
    }

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值<br/>
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值.
     *
     * @param edge  边
     * @param value 边的值
     * @return 边的值
     */
    @Override
    public V putEdgeValue(final Edge<N> edge, final V value) {
        return directedValueGraph.putEdgeValue(EndpointPair.ordered(edge.source(), edge.target()), value);
    }

    /**
     * 删除指定节点，如果它在图中存在。跟这个节点相关的边也会被删除.
     *
     * @param node 指定的节点
     * @return true 删除成功，false 删除失败
     */
    @Override
    public boolean removeNode(final N node) {
        return directedValueGraph.removeNode(node);
    }

    /**
     * 有向图：删除 nodeU -> nodeV 这样的边 如果在图中存在的话<br/>
     * 无向图：删除 nodeU - nodeV 这样的边 如果在图中存在的话.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return 边的值
     */
    @Override
    public V removeEdge(final N nodeU, final N nodeV) {
        return directedValueGraph.removeEdge(nodeU, nodeV);
    }

    /**
     * 有向图：删除 nodeU -> nodeV 这样的边 如果在图中存在的话<br/>
     * 无向图：删除 nodeU - nodeV 这样的边 如果在图中存在的话.
     *
     * @param edge 边
     * @return 边的值
     */
    @Override
    public V removeEdge(final Edge<N> edge) {
        return directedValueGraph.removeEdge(EndpointPair.ordered(edge.source(), edge.target()));
    }

    /**
     * 返回图中所有的节点.
     *
     * @return 当前图中所有的节点
     */
    @Override
    public Set<N> nodes() {
        return directedValueGraph.nodes();
    }

    /**
     * 返回图中所有的边.
     *
     * @return 图中所有的边
     */
    @Override
    public Set<Edge<N>> edges() {
        Set<EndpointPair<N>> edges = directedValueGraph.edges();
        if (edges != null && edges.size() > 0) {
            return edges.stream().map(pair -> Edge.ordered(pair.source(), pair.target())).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }

    /**
     * 图是否有向.
     *
     * @return true 有向，false 无向
     */
    @Override
    public boolean isDirected() {
        return directedValueGraph.isDirected();
    }

    /**
     * 是否允许边的源头和终点是同一个节点.
     *
     * @return true 允许，false 不允许
     */
    @Override
    public boolean allowsSelfLoops() {
        return directedValueGraph.allowsSelfLoops();
    }

    /**
     * 获取指定节点的关联节点，等于指定节点的前驱节点 + 后驱节点.
     *
     * @param node 指定节点
     * @return 通过线直接相连的节点
     */
    @Override
    public Set<N> adjacentNodes(final N node) {
        return directedValueGraph.adjacentNodes(node);
    }

    /**
     * 有向图：获取指定节点的所有的前驱节点(关联边的source节点)<br/>
     * 无向图：等于 adjacentNodes.
     *
     * @param node 指定节点
     * @return 根据方向返回前驱或者周围所有的节点
     */
    @Override
    public Set<N> predecessors(final N node) {
        return directedValueGraph.predecessors(node);
    }

    /**
     * 有向图：获取指定节点所有的后驱节点(关联边的target节点)<br/>
     * 无向图：等于 adjacentNodes(node).
     *
     * @param node 指定节点
     * @return 根据方向返回前驱或者周围所有的节点
     */
    @Override
    public Set<N> successors(final N node) {
        return directedValueGraph.successors(node);
    }

    /**
     * 有向图：获取指定节点的所有关联的边<br/>
     * 无向图：获取指定节点的所有关联的边.
     *
     * @param node 指定节点
     * @return 返回所有直接关联边的结合
     */
    @Override
    public Set<Edge<N>> incidentEdges(final N node) {
        Set<EndpointPair<N>> endpointPairs = directedValueGraph.incidentEdges(node);
        if (endpointPairs != null && endpointPairs.size() > 0) {
            return endpointPairs.stream().map(pair -> Edge.ordered(pair.source(), pair.target())).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }

    /**
     * 有向图：获取指定节点相关联边的数量(出边 + 入边)<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回直接关联边的数量
     */
    @Override
    public int degree(final N node) {
        return directedValueGraph.degree(node);
    }

    /**
     * 有向图：获取指定节点入边的数量<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回入边的数量，或所有直接关联边的数量
     */
    @Override
    public int inDegree(final N node) {
        return directedValueGraph.inDegree(node);
    }

    /**
     * 有向图：获取指定节点出边的数量<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回出边的数量，或所有直接关联边的数量
     */
    @Override
    public int outDegree(final N node) {
        return directedValueGraph.outDegree(node);
    }

    /**
     * 有向图：判断当前图中是否存在这样的边 nodeU -> nodeV<br/>
     * 无向图：判断当前图中是否存在这样的边 nodeU - nodeV，nodeV - nodeU.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return true 有关联，false 无关联
     */
    @Override
    public boolean hasEdgeConnecting(final N nodeU, final N nodeV) {
        return directedValueGraph.hasEdgeConnecting(nodeU, nodeV);
    }

    /**
     * 有向图：判断当前图中是否存在这样的边 nodeU -> nodeV<br/>
     * 无向图：判断当前图中是否存在这样的边 nodeU - nodeV，nodeV - nodeU.
     *
     * @return true 有关联，false 无关联
     */
    @Override
    public boolean hasEdgeConnecting(final Edge<N> edge) {
        return directedValueGraph.hasEdgeConnecting(EndpointPair.ordered(edge.source(), edge.target()));
    }

    /**
     * 有向图：如果途中存在 nodeU -> nodeV 则返回该边的值，否则返回null<br/>
     * 无向图：如果途中存在 nodeU - nodeV 则返回该边的值，否则返回null.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return Optional 边的值
     */
    @Override
    public Optional<V> edgeValue(final N nodeU, final N nodeV) {
        return directedValueGraph.edgeValue(nodeU, nodeV);
    }

    /**
     * 有向图：如果途中存在 nodeU -> nodeV 则返回该边的值，否则返回null<br/>
     * 无向图：如果途中存在 nodeU - nodeV 则返回该边的值，否则返回null.
     *
     * @param edge 边
     * @return Optional 边的值
     */
    @Override
    public Optional<V> edgeValue(final Edge<N> edge) {
        return directedValueGraph.edgeValue(EndpointPair.ordered(edge.source(), edge.target()));
    }

    /**
     * 有向图：如果图中包含 nodeU -> nodeV 的边则返回该边对应的值，否则返回defaultValue<br/>
     * 无向图：如果图中包含 nodeU - nodeV 的边则返回该边对应的值，否则返回defaultValue.
     *
     * @param nodeU        nodeU
     * @param nodeV        nodeV
     * @param defaultValue 默认值
     * @return 边的值
     */
    @Override
    public V edgeValueOrDefault(final N nodeU, final N nodeV, final V defaultValue) {
        return directedValueGraph.edgeValueOrDefault(nodeU, nodeV, defaultValue);
    }

    /**
     * 有向图：如果图中包含 nodeU -> nodeV 的边则返回该边对应的值，否则返回defaultValue<br/>
     * 无向图：如果图中包含 nodeU - nodeV 的边则返回该边对应的值，否则返回defaultValue.
     *
     * @param edge         edge
     * @param defaultValue 默认值
     * @return 边的值
     */
    @Override
    public V edgeValueOrDefault(final Edge<N> edge, final V defaultValue) {
        return directedValueGraph.edgeValueOrDefault(EndpointPair.ordered(edge.source(), edge.target()), defaultValue);
    }

}
