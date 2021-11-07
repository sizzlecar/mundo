package com.bluslee.mundo.core.process.graph;


import java.util.Arrays;


/**
 * @author carl.che
 * @date 2021/11/3
 * @description Edge 图中的边的基类，参考了guava中的定义
 */
public abstract class Edge<N> {
    private final N nodeU;
    private final N nodeV;

    private Edge(N nodeU, N nodeV) {
        this.nodeU = nodeU;
        this.nodeV = nodeV;
    }

    /**
     * 有向边
     */
    public static <N> Edge<N> ordered(N source, N target) {
        return new Edge.Ordered<N>(source, target);
    }

    /**
     * 无向边
     */
    public static <N> Edge<N> unordered(N nodeU, N nodeV) {
        // Swap nodes on purpose to prevent callers from relying on the "ordering" of an unordered pair.
        return new Edge.Unordered<N>(nodeV, nodeU);
    }

    /**
     * 根据图是否有向创建边
     */
    static <N> Edge<N> of(BaseGraph<?> graph, N nodeU, N nodeV) {
        return graph.isDirected() ? ordered(nodeU, nodeV) : unordered(nodeU, nodeV);
    }

    public abstract boolean isOrdered();

    /**
     * 有向边的出发节点
     */
    public abstract N source();

    /**
     * 有向边的目标节点
     */
    public abstract N target();

    /**
     * 有向边返回source
     */
    public final N nodeU() {
        return nodeU;
    }

    /**
     * 有向边返回target
     */
    public final N nodeV() {
        return nodeV;
    }

    /**
     * 如果node属于当前边的一端，那么返回另一端的节点
     */
    public final N adjacentNode(N node) {
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
        private Ordered(N source, N target) {
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
        public boolean equals(Object obj) {
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
        private Unordered(N nodeU, N nodeV) {
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
        public boolean equals(Object obj) {
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

            if (nodeU().equals(other.nodeU())) { // check condition1
                return nodeV().equals(other.nodeV());
            }
            return nodeU().equals(other.nodeV()) && nodeV().equals(other.nodeU()); // check condition2
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
