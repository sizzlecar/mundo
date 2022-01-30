package com.bluslee.mundo.core.process.graph;

import java.util.Arrays;

/**
 * Edge 图中的边的基类.
 * @param <N> 节点的类型
 * @author carl.che
 */
public abstract class Edge<N> {
    private final N nodeU;

    private final N nodeV;

    private Edge(final N nodeU, final N nodeV) {
        this.nodeU = nodeU;
        this.nodeV = nodeV;
    }

    /**
     * 构建有向边.
     *
     * @param <N>    节点类型
     * @param source 出发节点
     * @param target 目的节点
     * @return 有向边
     */
    public static <N> Edge<N> ordered(final N source, final N target) {
        return new Edge.Ordered<N>(source, target);
    }

    /**
     * 构建无向边.
     *
     * @param <N>   节点类型
     * @param nodeU nodeU
     * @param nodeV nodeV
     * @return 无向边
     */
    public static <N> Edge<N> unordered(final N nodeU, final N nodeV) {
        // Swap nodes on purpose to prevent callers from relying on the "ordering" of an unordered pair.
        return new Edge.Unordered<N>(nodeV, nodeU);
    }

    /**
     * 根据图是否有向创建边.
     *
     * @param <N>   节点类型
     * @param nodeV nodeV
     * @param nodeU nodeU
     * @param graph 图
     */
    static <N> Edge<N> of(final BaseGraph<?> graph, final N nodeU, final N nodeV) {
        return graph.isDirected() ? ordered(nodeU, nodeV) : unordered(nodeU, nodeV);
    }

    /**
     * 边是否有向.
     *
     * @return true 有向，false 无向
     */
    public abstract boolean isOrdered();

    /**
     * 有向边的出发节点.
     *
     * @return 出发节点
     */
    public abstract N source();

    /**
     * 有向边的目标节点.
     *
     * @return 目标节点
     */
    public abstract N target();

    /**
     * 有向边的出发节点,
     * 无向边的nodeU.
     *
     * @return 有向边的出发节点,无向边的nodeU
     */
    public final N nodeU() {
        return nodeU;
    }

    /**
     * 有向边的结束节点,
     * 无向边的nodeV.
     *
     * @return 有向边的出发节点,无向边的nodeU
     */
    public final N nodeV() {
        return nodeV;
    }

    /**
     * 如果node属于当前边的一端，那么返回另一端的节点.
     *
     * @param node 指定的节点
     * @return 边的另外一段的节点
     */
    public final N adjacentNode(final N node) {
        if (node.equals(nodeU)) {
            return nodeV;
        } else if (node.equals(nodeV)) {
            return nodeU;
        } else {
            throw new IllegalArgumentException("edge " + this + " does not contain node " + node);
        }
    }

    /**
     * Two ordered {@link Edge}s are equal if their {@link #source()} and {@link #target()}
     * are equal. Two unordered {@link Edge}s are equal if they contain the same nodes. An
     * ordered {@link Edge} is never equal to an unordered {@link Edge}.
     *
     * @param obj 待比较的对象
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * The hashcode of an ordered {@link Edge} is equal to {@code Objects.hashCode(source(),
     * target())}. The hashcode of an unordered {@link Edge} is equal to {@code
     * nodeU().hashCode() + nodeV().hashCode()}.
     */
    @Override
    public abstract int hashCode();

    private static final class Ordered<N> extends Edge<N> {
        private Ordered(final N source, final N target) {
            super(source, target);
        }

        @Override
        public N source() {
            return nodeU();
        }

        @Override
        public N target() {
            return nodeV();
        }

        @Override
        public boolean isOrdered() {
            return true;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Edge)) {
                return false;
            }

            Edge<?> other = (Edge<?>) obj;
            if (isOrdered() != other.isOrdered()) {
                return false;
            }

            return source().equals(other.source()) && target().equals(other.target());
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{source(), target()});
        }

        @Override
        public String toString() {
            return "<" + source() + " -> " + target() + ">";
        }
    }

    private static final class Unordered<N> extends Edge<N> {
        private Unordered(final N nodeU, final N nodeV) {
            super(nodeU, nodeV);
        }

        @Override
        public N source() {
            throw new UnsupportedOperationException();
        }

        @Override
        public N target() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isOrdered() {
            return false;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Edge)) {
                return false;
            }

            Edge<?> other = (Edge<?>) obj;
            if (isOrdered() != other.isOrdered()) {
                return false;
            }

            if (nodeU().equals(other.nodeU())) {
                return nodeV().equals(other.nodeV());
            }
            return nodeU().equals(other.nodeV()) && nodeV().equals(other.nodeU());
        }

        @Override
        public int hashCode() {
            return nodeU().hashCode() + nodeV().hashCode();
        }

        @Override
        public String toString() {
            return "[" + nodeU() + ", " + nodeV() + "]";
        }
    }

}
