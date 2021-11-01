package com.bluslee.mundo.core.graph;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * 有向值图接口，接口定义参考了guava中图的定
 * @param <N> 节点类型
 * @param <V> 值的类型
 */
public interface DirectedValueGraph<N, V> {
  //
  // ValueGraph-level accessors
  //

  /** Returns all nodes in this graph, in the order specified. */
  Set<N> nodes();

  /** Returns all edges in this graph. */
  Set<EndpointPair<N>> edges();


  /**
   * Returns true if this graph allows self-loops (edges that connect a node to itself). Attempting
   * to add a self-loop to a graph that does not allow them will throw an {@link
   * IllegalArgumentException}.
   */
  boolean allowsSelfLoops();

  //
  // Element-level accessors
  //

  /**
   * Returns the nodes which have an incident edge in common with {@code node} in this graph.
   *
   * <p>This is equal to the union of {@link #predecessors(Object)} and {@link #successors(Object)}.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  Set<N> adjacentNodes(N node);

  /**
   * Returns all nodes in this graph adjacent to {@code node} which can be reached by traversing
   * {@code node}'s incoming edges <i>against</i> the direction (if any) of the edge.
   *
   * <p>In an undirected graph, this is equivalent to {@link #adjacentNodes(Object)}.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  Set<N> predecessors(N node);

  /**
   * Returns all nodes in this graph adjacent to {@code node} which can be reached by traversing
   * {@code node}'s outgoing edges in the direction (if any) of the edge.
   *
   * <p>In an undirected graph, this is equivalent to {@link #adjacentNodes(Object)}.
   *
   * <p>This is <i>not</i> the same as "all nodes reachable from {@code node} by following outgoing
   * edges".
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  Set<N> successors(N node);

  /**
   * Returns the edges in this graph whose endpoints include {@code node}.
   *
   * <p>This is equal to the union of incoming and outgoing edges.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   * @since 24.0
   */
  Set<EndpointPair<N>> incidentEdges(N node);

  /**
   * Returns the count of {@code node}'s incident edges, counting self-loops twice (equivalently,
   * the number of times an edge touches {@code node}).
   *
   * <p>For directed graphs, this is equal to {@code inDegree(node) + outDegree(node)}.
   *
   * <p>For undirected graphs, this is equal to {@code incidentEdges(node).size()} + (number of
   * self-loops incident to {@code node}).
   *
   * <p>If the count is greater than {@code Integer.MAX_VALUE}, returns {@code Integer.MAX_VALUE}.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  int degree(N node);

  /**
   * Returns the count of {@code node}'s incoming edges (equal to {@code predecessors(node).size()})
   * in a directed graph. In an undirected graph, returns the {@link #degree(Object)}.
   *
   * <p>If the count is greater than {@code Integer.MAX_VALUE}, returns {@code Integer.MAX_VALUE}.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  int inDegree(N node);

  /**
   * Returns the count of {@code node}'s outgoing edges (equal to {@code successors(node).size()})
   * in a directed graph. In an undirected graph, returns the {@link #degree(Object)}.
   *
   * <p>If the count is greater than {@code Integer.MAX_VALUE}, returns {@code Integer.MAX_VALUE}.
   *
   * @throws IllegalArgumentException if {@code node} is not an element of this graph
   */
  int outDegree(N node);

  /**
   * Returns true if there is an edge that directly connects {@code nodeU} to {@code nodeV}. This is
   * equivalent to {@code nodes().contains(nodeU) && successors(nodeU).contains(nodeV)}.
   *
   * <p>In an undirected graph, this is equal to {@code hasEdgeConnecting(nodeV, nodeU)}.
   *
   * @since 23.0
   */
  boolean hasEdgeConnecting(N nodeU, N nodeV);

  /**
   * Returns true if there is an edge that directly connects {@code endpoints} (in the order, if
   * any, specified by {@code endpoints}). This is equivalent to {@code
   * edges().contains(endpoints)}.
   *
   * <p>Unlike the other {@code EndpointPair}-accepting methods, this method does not throw if the
   * endpoints are unordered and the graph is directed; it simply returns {@code false}. This is for
   * consistency with the behavior of {@link Collection#contains(Object)} (which does not generally
   * throw if the object cannot be present in the collection), and the desire to have this method's
   * behavior be compatible with {@code edges().contains(endpoints)}.
   *
   * @since 27.1
   */
  boolean hasEdgeConnecting(EndpointPair<N> endpoints);

  /**
   * Returns the value of the edge that connects {@code nodeU} to {@code nodeV} (in the order, if
   * any, specified by {@code endpoints}), if one is present; otherwise, returns {@code
   * Optional.empty()}.
   *
   * @throws IllegalArgumentException if {@code nodeU} or {@code nodeV} is not an element of this
   *     graph
   * @since 23.0 (since 20.0 with return type {@code V})
   */
  Optional<V> edgeValue(N nodeU, N nodeV);

  /**
   * Returns the value of the edge that connects {@code endpoints} (in the order, if any, specified
   * by {@code endpoints}), if one is present; otherwise, returns {@code Optional.empty()}.
   *
   * <p>If this graph is directed, the endpoints must be ordered.
   *
   * @throws IllegalArgumentException if either endpoint is not an element of this graph
   * @throws IllegalArgumentException if the endpoints are unordered and the graph is directed
   * @since 27.1
   */
  Optional<V> edgeValue(EndpointPair<N> endpoints);

  /**
   * Returns the value of the edge that connects {@code nodeU} to {@code nodeV}, if one is present;
   * otherwise, returns {@code defaultValue}.
   *
   * <p>In an undirected graph, this is equal to {@code edgeValueOrDefault(nodeV, nodeU,
   * defaultValue)}.
   *
   * @throws IllegalArgumentException if {@code nodeU} or {@code nodeV} is not an element of this
   *     graph
   */
  V edgeValueOrDefault(N nodeU, N nodeV, V defaultValue);

  /**
   * Returns the value of the edge that connects {@code endpoints} (in the order, if any, specified
   * by {@code endpoints}), if one is present; otherwise, returns {@code defaultValue}.
   *
   * <p>If this graph is directed, the endpoints must be ordered.
   *
   * @throws IllegalArgumentException if either endpoint is not an element of this graph
   * @throws IllegalArgumentException if the endpoints are unordered and the graph is directed
   * @since 27.1
   */
  V edgeValueOrDefault(EndpointPair<N> endpoints, V defaultValue);

  //
  // ValueGraph identity
  //

  /**
   * Returns {@code true} iff {@code object} is a {@link DirectedValueGraph} that has the same elements and
   * the same structural relationships as those in this graph.
   *
   * <p>Thus, two value graphs A and B are equal if <b>all</b> of the following are true:
   *
   * <ul>
   *   <li>A and B have equal {@link #nodes() node sets}.
   *   <li>A and B have equal {@link #edges() edge sets}.
   *   <li>The {@link #edgeValue(Object, Object) value} of a given edge is the same in both A and B.
   * </ul>
   *
   * <p>Graph properties besides  do <b>not</b> affect equality.
   * For example, two graphs may be considered equal even if one allows self-loops and the other
   * doesn't. Additionally, the order in which nodes or edges are added to the graph, and the order
   * in which they are iterated over, are irrelevant.
   *
   */
  @Override
  boolean equals(Object object);

  /**
   * Returns the hash code for this graph. The hash code of a graph is defined as the hash code of a
   * map from each of its {@link #edges() edges} to the associated {@link #edgeValue(Object, Object)
   * edge value}.
   *
   */
  @Override
  int hashCode();
}
