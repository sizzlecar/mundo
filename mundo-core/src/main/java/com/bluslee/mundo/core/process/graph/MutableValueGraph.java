package com.bluslee.mundo.core.process.graph;

import java.util.Optional;

/**
 * 值图接口，代表图中的边可以有自己的值.
 *
 * @param <N> Node parameter type
 * @param <V> Value parameter type
 * @author carl.che
 */
public interface MutableValueGraph<N, V> extends BaseGraph<N> {

    /**
     * 有向图：如果途中存在 nodeU -> nodeV 则返回该边的值，否则返回null<br/>
     * 无向图：如果途中存在 nodeU - nodeV 则返回该边的值，否则返回null.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return Optional 边的值
     */
    Optional<V> edgeValue(N nodeU, N nodeV);

    /**
     * 有向图：如果途中存在 nodeU -> nodeV 则返回该边的值，否则返回null<br/>
     * 无向图：如果途中存在 nodeU - nodeV 则返回该边的值，否则返回null.
     *
     * @param edge 边
     * @return Optional 边的值
     */
    Optional<V> edgeValue(Edge<N> edge);

    /**
     * 有向图：如果图中包含 nodeU -> nodeV 的边则返回该边对应的值，否则返回defaultValue<br/>
     * 无向图：如果图中包含 nodeU - nodeV 的边则返回该边对应的值，否则返回defaultValue.
     *
     * @param nodeU        nodeU
     * @param nodeV        nodeV
     * @param defaultValue 默认值
     * @return 边的值
     */
    V edgeValueOrDefault(N nodeU, N nodeV, V defaultValue);

    /**
     * 有向图：如果图中包含 nodeU -> nodeV 的边则返回该边对应的值，否则返回defaultValue<br/>
     * 无向图：如果图中包含 nodeU - nodeV 的边则返回该边对应的值，否则返回defaultValue.
     *
     * @param edge         edge
     * @param defaultValue 默认值
     * @return 边的值
     */
    V edgeValueOrDefault(Edge<N> edge, V defaultValue);

    /**
     * 如果该节点在途中不存在则向图添加一个节点.
     *
     * @param node 添加的节点
     * @return true 添加成功 false 添加失败
     */
    boolean addNode(N node);

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值<br/>
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值.
     *
     * @param nodeV nodeV
     * @param nodeU nodeU
     * @param value 边的值
     * @return 边的值
     */
    V putEdgeValue(N nodeU, N nodeV, V value);

    /**
     * 有向图：向图中添加一条 nodeU -> nodeV 值为value的边，如果边已存在则更新值<br/>
     * 无向图：向图中添加一条 nodeU - nodeV 值为value的边，如果边已存在则更新值.
     *
     * @param edge  边
     * @param value 边的值
     * @return 边的值
     */
    V putEdgeValue(Edge<N> edge, V value);

    /**
     * 删除指定节点，如果它在图中存在。跟这个节点相关的边也会被删除.
     *
     * @param node 指定的节点
     * @return true 删除成功，false 删除失败
     */
    boolean removeNode(N node);

    /**
     * 有向图：删除 nodeU -> nodeV 这样的边 如果在图中存在的话<br/>
     * 无向图：删除 nodeU - nodeV 这样的边 如果在图中存在的话.
     *
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return 边的值
     */
    V removeEdge(N nodeU, N nodeV);

    /**
     * 有向图：删除 nodeU -> nodeV 这样的边 如果在图中存在的话<br/>
     * 无向图：删除 nodeU - nodeV 这样的边 如果在图中存在的话.
     *
     * @param edge 边
     * @return 边的值
     */
    V removeEdge(Edge<N> edge);
}
