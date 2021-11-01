package com.bluslee.mundo.core.graph;


/**
 * 可变有向值图，接口定义参考了guava中的定义
 * @param <N>
 * @param <V>
 */
public interface MutableValueGraph<N, V> extends DirectedValueGraph<N, V> {

  /**
   * Adds {@code node} if it is not already present.
   *
   * <p><b>Nodes must be unique</b>, just as {@code Map} keys must be. They must also be non-null.
   *
   * @return {@code true} if the graph was modified as a result of this call
   */
  boolean addNode(N node);

  /**
   * Adds an edge connecting {@code nodeU} to {@code nodeV} if one is not already present, and sets
   * a value for that edge to {@code value} (overwriting the existing value, if any).
   *
   * <p>If the graph is directed, the resultant edge will be directed; otherwise, it will be
   * undirected.
   *
   * <p>Values do not have to be unique. However, values must be non-null.
   *
   * <p>If {@code nodeU} and {@code nodeV} are not already present in this graph, this method will
   * silently {@link #addNode(Object) add} {@code nodeU} and {@code nodeV} to the graph.
   *
   * @return the value previously associated with the edge connecting {@code nodeU} to {@code
   *     nodeV}, or null if there was no such edge.
   * @throws IllegalArgumentException if the introduction of the edge would violate {@link
   *     #allowsSelfLoops()}
   */
  V putEdgeValue(N nodeU, N nodeV, V value);

  /**
   * Adds an edge connecting {@code endpoints} if one is not already present, and sets a value for
   * that edge to {@code value} (overwriting the existing value, if any).
   *
   * <p>If the graph is directed, the resultant edge will be directed; otherwise, it will be
   * undirected.
   *
   * <p>If this graph is directed, {@code endpoints} must be ordered.
   *
   * <p>Values do not have to be unique. However, values must be non-null.
   *
   * <p>If either or both endpoints are not already present in this graph, this method will silently
   * {@link #addNode(Object) add} each missing endpoint to the graph.
   *
   * @return the value previously associated with the edge connecting {@code nodeU} to {@code
   *     nodeV}, or null if there was no such edge.
   * @throws IllegalArgumentException if the introduction of the edge would violate {@link
   *     #allowsSelfLoops()}
   * @throws IllegalArgumentException if the endpoints are unordered and the graph is directed
   * @since 27.1
   */
  V putEdgeValue(EndpointPair<N> endpoints, V value);

  /**
   * Removes {@code node} if it is present; all edges incident to {@code node} will also be removed.
   *
   * @return {@code true} if the graph was modified as a result of this call
   */
  boolean removeNode(N node);

  /**
   * Removes the edge connecting {@code nodeU} to {@code nodeV}, if it is present.
   *
   * @return the value previously associated with the edge connecting {@code nodeU} to {@code
   *     nodeV}, or null if there was no such edge.
   */
  V removeEdge(N nodeU, N nodeV);

  /**
   * Removes the edge connecting {@code endpoints}, if it is present.
   *
   * <p>If this graph is directed, {@code endpoints} must be ordered.
   *
   * @return the value previously associated with the edge connecting {@code endpoints}, or null if
   *     there was no such edge.
   * @since 27.1
   */
  V removeEdge(EndpointPair<N> endpoints);
}
