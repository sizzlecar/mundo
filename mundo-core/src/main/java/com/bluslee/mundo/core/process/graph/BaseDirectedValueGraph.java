package com.bluslee.mundo.core.process.graph;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 有向图基类，提供图的基础服务.
 *
 * @author carl.che
 * @see MutableValueGraph
 */
public abstract class BaseDirectedValueGraph<N extends BaseProcessNode, V> implements MutableValueGraph<N, V> {

    /**
     * 获取当前图中从指定节点出发的边.
     *
     * @param node 指定节点
     * @return 从指定节点出发的边
     */
    @Override
    public Set<Edge<N>> outgoingEdges(final N node) {
        //获取指定节点所有的边
        Set<Edge<N>> endpointPairs = incidentEdges(node);
        //source == node 的就是outgoingEdges
        return endpointPairs.stream()
                .filter(pair -> pair.source().getId().equals(node.getId())).collect(Collectors.toSet());
    }

    /**
     * 获取当前图中到达指定节点的边.
     *
     * @param node 指定节点
     * @return 到达指定节点的边
     */
    @Override
    public Set<Edge<N>> incomingEdges(final N node) {
        //获取指定节点所有的边
        Set<Edge<N>> endpointPairs = incidentEdges(node);
        //target == node 的就是outgoingEdges
        return endpointPairs.stream()
                .filter(pair -> pair.target().getId().equals(node.getId())).collect(Collectors.toSet());
    }
}
