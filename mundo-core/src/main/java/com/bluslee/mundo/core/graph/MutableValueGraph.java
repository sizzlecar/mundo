package com.bluslee.mundo.core.graph;


import java.util.Optional;

/**
 * @author carl.che
 * @date 2021/11/4
 * @description MutableValueGraph
 * @param <N> Node parameter type
 * @param <V> Value parameter type
 */
public interface MutableValueGraph<N, V> extends BaseGraph<N>{

    /**
     * 有向图：如果途中存在 nodeU -> nodeV 则返回该边的值，否则返回null
     * 无向图：如果途中存在 nodeU - nodeV 则返回该边的值，否则返回null
     */
    Optional<V> edgeValue(N nodeU, N nodeV);

    /**
     * 见edgeValue
     */
    Optional<V> edgeValue(Edge<N> endpoints);

    /**
     * 有向图：如果图中包含 nodeU -> nodeV 的边则返回该边对应的值，否则返回defaultValue
     * 无向图：如果图中包含 nodeU - nodeV 的边则返回该边对应的值，否则返回defaultValue
     */
    V edgeValueOrDefault(N nodeU, N nodeV, V defaultValue);

    /**
     * 见 edgeValueOrDefault(N nodeU, N nodeV, V defaultValue);
     */
    V edgeValueOrDefault(Edge<N> endpoints, V defaultValue);

    /**
     * 如果该节点在途中不存在则向图添加一个节点
     */
    boolean addNode(N node);

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值
     */
    V putEdgeValue(N nodeU, N nodeV, V value);

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值
     */
    V putEdgeValue(Edge<N> edge, V value);

    /**
     * 删除指定节点，如果它在图中存在。跟这个节点相关的边也会被删除
     */
    boolean removeNode(N node);

    /**
     * 有向图：删除 nodeU -> nodeV 这样的边 如果在图中存在的话
     * 无向图：删除 nodeU - nodeV 这样的边 如果在图中存在的话
     */
    V removeEdge(N nodeU, N nodeV);

    /**
     * 见 removeEdge(N nodeU, N nodeV)
     */
    V removeEdge(Edge<N> edge);
}
