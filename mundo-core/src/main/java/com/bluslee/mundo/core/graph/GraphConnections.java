package com.bluslee.mundo.core.graph;


import java.util.Iterator;
import java.util.Set;

/**
 * 图connection接口定义，参考了guava中图的定义
 * @param <N> 节点类型
 * @param <V> 边的值的类型
 */
interface GraphConnections<N, V> {

  Set<N> adjacentNodes();

  Set<N> predecessors();

  Set<N> successors();

  /**
   * Returns an iterator over the incident edges.
   *
   * @param thisNode The node that this all of the connections in this class are connected to.
   */
  Iterator<EndpointPair<N>> incidentEdgeIterator(N thisNode);

  /**
   * Returns the value associated with the edge connecting the origin node to {@code node}, or null
   * if there is no such edge.
   */
  V value(N node);

  /** Remove {@code node} from the set of predecessors. */
  void removePredecessor(N node);

  /**
   * Remove {@code node} from the set of successors. Returns the value previously associated with
   * the edge connecting the two nodes.
   */
  V removeSuccessor(N node);

  /**
   * Add {@code node} as a predecessor to the origin node. In the case of an undirected graph, it
   * also becomes a successor. Associates {@code value} with the edge connecting the two nodes.
   */
  void addPredecessor(N node, V value);

  /**
   * Add {@code node} as a successor to the origin node. In the case of an undirected graph, it also
   * becomes a predecessor. Associates {@code value} with the edge connecting the two nodes. Returns
   * the value previously associated with the edge connecting the two nodes.
   */
  V addSuccessor(N node, V value);
}
