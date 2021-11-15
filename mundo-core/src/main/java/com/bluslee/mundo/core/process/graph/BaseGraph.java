package com.bluslee.mundo.core.process.graph;

import java.util.Set;

/**
 * 图的基础接口，提供图的基础服务.
 *
 * @param <N> 图中元素类型
 * @author carl.che
 */
public interface BaseGraph<N> {

    /**
     * 返回图中所有的节点.
     *
     * @return 当前图中所有的节点
     */
    Set<N> nodes();

    /**
     * 返回图中所有的边.
     *
     * @return 图中所有的边
     */
    Set<Edge<N>> edges();

    /**
     * 图是否有向.
     *
     * @return true 有向，false 无向
     */
    boolean isDirected();

    /**
     * 是否允许边的源头和终点是同一个节点.
     *
     * @return true 允许，false 不允许
     */
    boolean allowsSelfLoops();

    /**
     * 获取指定节点的关联节点，等于指定节点的前驱节点 + 后驱节点.
     *
     * @param node 指定节点
     * @return 通过线直接相连的节点
     */
    Set<N> adjacentNodes(N node);

    /**
     * 有向图：获取指定节点的所有的前驱节点(关联边的source节点)<br/>
     * 无向图：等于 adjacentNodes.
     *
     * @param node 指定节点
     * @return 根据方向返回前驱或者周围所有的节点
     */
    Set<N> predecessors(N node);

    /**
     * 有向图：获取指定节点所有的后驱节点(关联边的target节点)<br/>
     * 无向图：等于 adjacentNodes(node).
     *
     * @param node 指定节点
     * @return 根据方向返回前驱或者周围所有的节点
     */
    Set<N> successors(N node);

    /**
     * 有向图：获取指定节点的所有关联的边<br/>
     * 无向图：获取指定节点的所有关联的边.
     *
     * @param node 指定节点
     * @return 返回所有直接关联边的结合
     */
    Set<Edge<N>> incidentEdges(N node);

    /**
     * 有向图：获取指定节点的所有出边<br/>
     * 无向图：获取指定节点的所有关联的边.
     *
     * @param node 指定节点
     * @return 返回出边集合，或所有直接关联边结合
     */
    Set<Edge<N>> outgoingEdges(N node);

    /**
     * 有向图：获取指定节点的所有入边<br/>
     * 无向图：获取指定节点的所有关联的边.
     *
     * @param node 指定节点
     * @return 返回入边集合，或所有直接关联边结合
     */
    Set<Edge<N>> incomingEdges(N node);

    /**
     * 有向图：获取指定节点相关联边的数量(出边 + 入边)<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回直接关联边的数量
     */
    int degree(N node);

    /**
     * 有向图：获取指定节点入边的数量<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回入边的数量，或所有直接关联边的数量
     */
    int inDegree(N node);

    /**
     * 有向图：获取指定节点出边的数量<br/>
     * 无向图：获取指定节点相关联边的数量.
     *
     * @param node 指定节点
     * @return 返回出边的数量，或所有直接关联边的数量
     */
    int outDegree(N node);

    /**
     * 有向图：判断当前图中是否存在这样的边 nodeU -> nodeV<br/>
     * 无向图：判断当前图中是否存在这样的边 nodeU - nodeV，nodeV - nodeU.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return true 有关联，false 无关联
     */
    boolean hasEdgeConnecting(N nodeU, N nodeV);

    /**
     * 有向图：判断当前图中是否存在这样的边 nodeU -> nodeV<br/>
     * 无向图：判断当前图中是否存在这样的边 nodeU - nodeV，nodeV - nodeU.
     *
     * @return true 有关联，false 无关联
     */
    boolean hasEdgeConnecting(Edge<N> edge);
}
