package com.bluslee.mundo.core.process.graph;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.ValueGraphBuilder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description DirectedValueGraphImpl 有向值图实现类
 */
public class DirectedValueGraphImpl<N extends BaseProcessNode, V> extends BaseDirectedValueGraph<N, V> {

    protected final com.google.common.graph.MutableValueGraph<N, V> directedValueGraph =
            ValueGraphBuilder.directed().allowsSelfLoops(true).build();

    @Override
    public boolean addNode(N node) {
        return directedValueGraph.addNode(node);
    }

    @Override
    public V  putEdgeValue(N nodeU, N nodeV, V  value) {
        return directedValueGraph.putEdgeValue(nodeU, nodeV, value);
    }

    @Override
    public V  putEdgeValue(Edge<N> edge, V  value) {
        return directedValueGraph.putEdgeValue(EndpointPair.ordered(edge.source(), edge.target()), value);
    }

    @Override
    public boolean removeNode(N node) {
        return directedValueGraph.removeNode(node);
    }

    @Override
    public V  removeEdge(N nodeU, N nodeV) {
        return directedValueGraph.removeEdge(nodeU, nodeV);
    }

    @Override
    public V  removeEdge(Edge<N> edge) {
        return directedValueGraph.removeEdge(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public Set<N> nodes() {
        return directedValueGraph.nodes();
    }

    @Override
    public Set<Edge<N>> edges() {
        Set<EndpointPair<N>> edges = directedValueGraph.edges();
        if (edges != null && edges.size() > 0) {
            return edges.stream().map(pair -> Edge.ordered(pair.source(), pair.target())).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
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
    public Set<Edge<N>> incidentEdges(N node) {
        Set<EndpointPair<N>> endpointPairs = directedValueGraph.incidentEdges(node);
        if (endpointPairs != null && endpointPairs.size() > 0) {
            return endpointPairs.stream().map(pair -> Edge.ordered(pair.source(), pair.target())).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
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
    public boolean hasEdgeConnecting(Edge<N> edge) {
        return directedValueGraph.hasEdgeConnecting(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public Optional<V> edgeValue(N nodeU, N nodeV) {
        return directedValueGraph.edgeValue(nodeU, nodeV);
    }

    @Override
    public Optional<V> edgeValue(Edge<N> edge) {
        return directedValueGraph.edgeValue(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public V  edgeValueOrDefault(N nodeU, N nodeV, V  defaultValue) {
        return directedValueGraph.edgeValueOrDefault(nodeU, nodeV, defaultValue);
    }

    @Override
    public V  edgeValueOrDefault(Edge<N> edge, V  defaultValue) {
        return directedValueGraph.edgeValueOrDefault(EndpointPair.ordered(edge.source(), edge.target()), defaultValue);
    }

}
