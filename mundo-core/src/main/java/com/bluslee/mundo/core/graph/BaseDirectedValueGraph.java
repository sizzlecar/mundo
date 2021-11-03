package com.bluslee.mundo.core.graph;

import com.bluslee.mundo.core.process.BaseProcessNode;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.ElementOrder;

import javax.annotation.CheckForNull;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description BaseDirectedValueGraph 有向图包装类
 */
public abstract class BaseDirectedValueGraph<N extends BaseProcessNode, V> implements MutableValueGraph<N, V>{

    protected final MutableValueGraph<N, V> directedValueGraph = ValueGraphBuilder.directed().allowsSelfLoops(true).build();

    @Override
    public boolean addNode(N node) {
        return directedValueGraph.addNode(node);
    }

    @CheckForNull
    @Override
    public V putEdgeValue(N nodeU, N nodeV, V value) {
        return directedValueGraph.putEdgeValue(nodeU, nodeV, value);
    }

    @CheckForNull
    @Override
    public V putEdgeValue(EndpointPair<N> endpoints, V value) {
        return directedValueGraph.putEdgeValue(endpoints, value);
    }

    @Override
    public boolean removeNode(N node) {
        return directedValueGraph.removeNode(node);
    }

    @CheckForNull
    @Override
    public V removeEdge(N nodeU, N nodeV) {
        return directedValueGraph.removeEdge(nodeU, nodeV);
    }

    @CheckForNull
    @Override
    public V removeEdge(EndpointPair<N> endpoints) {
        return directedValueGraph.removeEdge(endpoints);
    }

    @Override
    public Set<N> nodes() {
        return directedValueGraph.nodes();
    }

    @Override
    public Set<EndpointPair<N>> edges() {
        return directedValueGraph.edges();
    }

    @Override
    public Graph<N> asGraph() {
        return directedValueGraph.asGraph();
    }

    @Override
    public boolean isDirected() {
        return directedValueGraph.isDirected();
    }

    @Override
    public boolean allowsSelfLoops() {
        return directedValueGraph.allowsSelfLoops();
    }

    @Override
    public ElementOrder<N> nodeOrder() {
        return directedValueGraph.nodeOrder();
    }

    @Override
    public ElementOrder<N> incidentEdgeOrder() {
        return directedValueGraph.incidentEdgeOrder();
    }

    @Override
    public Set<N> adjacentNodes(N node) {
        return directedValueGraph.adjacentNodes(node);
    }

    @Override
    public Set<N> predecessors(N node) {
        return directedValueGraph.predecessors(node);
    }

    @Override
    public Set<N> successors(N node) {
        return directedValueGraph.successors(node);
    }

    @Override
    public Set<EndpointPair<N>> incidentEdges(N node) {
        return directedValueGraph.incidentEdges(node);
    }

    @Override
    public int degree(N node) {
        return directedValueGraph.degree(node);
    }

    @Override
    public int inDegree(N node) {
        return directedValueGraph.inDegree(node);
    }

    @Override
    public int outDegree(N node) {
        return directedValueGraph.outDegree(node);
    }

    @Override
    public boolean hasEdgeConnecting(N nodeU, N nodeV) {
        return directedValueGraph.hasEdgeConnecting(nodeU, nodeV);
    }

    @Override
    public boolean hasEdgeConnecting(EndpointPair<N> endpoints) {
        return directedValueGraph.hasEdgeConnecting(endpoints);
    }

    @Override
    public Optional<V> edgeValue(N nodeU, N nodeV) {
        return directedValueGraph.edgeValue(nodeU, nodeV);
    }

    @Override
    public Optional<V> edgeValue(EndpointPair<N> endpoints) {
        return directedValueGraph.edgeValue(endpoints);
    }

    @CheckForNull
    @Override
    public V edgeValueOrDefault(N nodeU, N nodeV, @CheckForNull V defaultValue) {
        return directedValueGraph.edgeValueOrDefault(nodeU, nodeV, defaultValue);
    }

    @CheckForNull
    @Override
    public V edgeValueOrDefault(EndpointPair<N> endpoints, @CheckForNull V defaultValue) {
        return directedValueGraph.edgeValueOrDefault(endpoints, defaultValue);
    }

    /**
     * 获取当前图中从指定节点出发的边
     * @param node 指定节点
     * @return 从指定节点出发的边
     */
    public Set<EndpointPair<N>> outgoingEdges(N node) {
        //获取指定节点所有的边
        Set<EndpointPair<N>> endpointPairs = incidentEdges(node);
        //source == node 的就是outgoingEdges
        return endpointPairs.stream()
                .filter(pair -> pair.source().getId().equals(node.getId())).collect(Collectors.toSet());
    }

    /**
     * 获取当前图中到达指定节点的边
     * @param node 指定节点
     * @return 到达指定节点的边
     */
    public Set<EndpointPair<N>> incomingEdges(N node) {
        //获取指定节点所有的边
        Set<EndpointPair<N>> endpointPairs = incidentEdges(node);
        //target == node 的就是outgoingEdges
        return endpointPairs.stream()
                .filter(pair -> pair.target().getId().equals(node.getId())).collect(Collectors.toSet());
    }
}
