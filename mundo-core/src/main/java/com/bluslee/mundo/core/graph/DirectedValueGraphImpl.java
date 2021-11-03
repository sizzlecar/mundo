package com.bluslee.mundo.core.graph;

import com.bluslee.mundo.core.process.BaseProcessNode;
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
public class DirectedValueGraphImpl extends BaseDirectedValueGraph<BaseProcessNode, String> {

    protected final com.google.common.graph.MutableValueGraph<BaseProcessNode, String> directedValueGraph =
            ValueGraphBuilder.directed().allowsSelfLoops(true).build();

    @Override
    public boolean addNode(BaseProcessNode node) {
        return directedValueGraph.addNode(node);
    }

    @Override
    public String putEdgeValue(BaseProcessNode nodeU, BaseProcessNode nodeV, String value) {
        return directedValueGraph.putEdgeValue(nodeU, nodeV, value);
    }

    @Override
    public String putEdgeValue(Edge<BaseProcessNode> edge, String value) {
        return directedValueGraph.putEdgeValue(EndpointPair.ordered(edge.source(), edge.target()), value);
    }

    @Override
    public boolean removeNode(BaseProcessNode node) {
        return directedValueGraph.removeNode(node);
    }

    @Override
    public String removeEdge(BaseProcessNode nodeU, BaseProcessNode nodeV) {
        return directedValueGraph.removeEdge(nodeU, nodeV);
    }

    @Override
    public String removeEdge(Edge<BaseProcessNode> edge) {
        return directedValueGraph.removeEdge(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public Set<BaseProcessNode> nodes() {
        return directedValueGraph.nodes();
    }

    @Override
    public Set<Edge<BaseProcessNode>> edges() {
        Set<EndpointPair<BaseProcessNode>> edges = directedValueGraph.edges();
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
    public Set<BaseProcessNode> adjacentNodes(BaseProcessNode node) {
        return directedValueGraph.adjacentNodes(node);
    }

    @Override
    public Set<BaseProcessNode> predecessors(BaseProcessNode node) {
        return directedValueGraph.predecessors(node);
    }

    @Override
    public Set<BaseProcessNode> successors(BaseProcessNode node) {
        return directedValueGraph.successors(node);
    }

    @Override
    public Set<Edge<BaseProcessNode>> incidentEdges(BaseProcessNode node) {
        Set<EndpointPair<BaseProcessNode>> endpointPairs = directedValueGraph.incidentEdges(node);
        if (endpointPairs != null && endpointPairs.size() > 0) {
            return endpointPairs.stream().map(pair -> Edge.ordered(pair.source(), pair.target())).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }

    @Override
    public int degree(BaseProcessNode node) {
        return directedValueGraph.degree(node);
    }

    @Override
    public int inDegree(BaseProcessNode node) {
        return directedValueGraph.inDegree(node);
    }

    @Override
    public int outDegree(BaseProcessNode node) {
        return directedValueGraph.outDegree(node);
    }

    @Override
    public boolean hasEdgeConnecting(BaseProcessNode nodeU, BaseProcessNode nodeV) {
        return directedValueGraph.hasEdgeConnecting(nodeU, nodeV);
    }

    @Override
    public boolean hasEdgeConnecting(Edge<BaseProcessNode> edge) {
        return directedValueGraph.hasEdgeConnecting(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public Optional<String> edgeValue(BaseProcessNode nodeU, BaseProcessNode nodeV) {
        return directedValueGraph.edgeValue(nodeU, nodeV);
    }

    @Override
    public Optional<String> edgeValue(Edge<BaseProcessNode> edge) {
        return directedValueGraph.edgeValue(EndpointPair.ordered(edge.source(), edge.target()));
    }

    @Override
    public String edgeValueOrDefault(BaseProcessNode nodeU, BaseProcessNode nodeV, String defaultValue) {
        return directedValueGraph.edgeValueOrDefault(nodeU, nodeV, defaultValue);
    }

    @Override
    public String edgeValueOrDefault(Edge<BaseProcessNode> edge, String defaultValue) {
        return directedValueGraph.edgeValueOrDefault(EndpointPair.ordered(edge.source(), edge.target()), defaultValue);
    }

}
