package com.bluslee.mundo.core.process;


import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description BaseGraph 图的基础接口，参考了guava中的定义
 * @param <N> Node parameter type
 */
public interface BaseGraph<N> {

    /**
     * 返回图中所有的节点
     */
    Set<N> nodes();

    /**
     * 返回图中所有的边
     */
    Set<Edge<N>> edges();

    /**
     * 图的边是否有向
     */
    boolean isDirected();

    /**
     * 是否允许边的源头和终点是同一个节点
     */
    boolean allowsSelfLoops();

    /**
     * 获取指定节点的关联节点，等于指定节点的前驱节点 + 后驱节点
     */
    Set<N> adjacentNodes(N node);

    /**
     * 有向图：获取指定节点的所有的前驱节点(关联边的source节点)
     * 无向图：等于 adjacentNodes
     */
    Set<N> predecessors(N node);

    /**
     * 有向图：获取指定节点所有的后驱节点(关联边的target节点)
     * 无向图：等于 adjacentNodes(node)
     */
    Set<N> successors(N node);

    /**
     * 有向图：获取指定节点的所有关联的边(出边 + 入边)
     * 无向图：获取指定节点的所有关联的边
     */
    Set<Edge<N>> incidentEdges(N node);

    /**
     * 有向图：获取指定节点的所有出边
     * 无向图：获取指定节点的所有关联的边
     */
    Set<Edge<N>> outgoingEdges(N node);

    /**
     * 有向图：获取指定节点的所有入边
     * 无向图：获取指定节点的所有关联的边
     */
    Set<Edge<N>> incomingEdges(N node);

    /**
     * 有向图：获取指定节点相关联边的数量(出边 + 入边)
     * 无向图：获取指定节点相关联边的数量
     */
    int degree(N node);

    /**
     * 有向图：获取指定节点入边的数量
     * 无向图：获取指定节点相关联边的数量
     */
    int inDegree(N node);

    /**
     * 有向图：获取指定节点出边的数量
     * 无向图：获取指定节点相关联边的数量
     */
    int outDegree(N node);

    /**
     * 有向图：判断当前图中是否存在这样的边 nodeU -> nodeV
     * 无向图：判断当前图中是否存在这样的边 nodeU - nodeV，nodeV - nodeU
     */
    boolean hasEdgeConnecting(N nodeU, N nodeV);

    /**
     * 见hasEdgeConnecting
     */
    boolean hasEdgeConnecting(Edge<N> edge);
}
