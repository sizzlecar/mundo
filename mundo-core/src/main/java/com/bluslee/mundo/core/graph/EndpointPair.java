package com.bluslee.mundo.core.graph;


import java.util.Arrays;

/**
 * 有向节点对
 * @param <N> 节点类型
 */
public abstract class EndpointPair<N> {
    private final N source;
    private final N target;

    private EndpointPair(N source, N target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Returns an {@link EndpointPair} representing the endpoints of a directed edge.
     */
    public static <N> EndpointPair<N> ordered(N source, N target) {
        return new EndpointPairImpl<>(source, target);
    }

    /**
     * returns the node which is the source.
     */
    public final N source() {
        return source;
    }

    /**
     * returns the node which is the target.
     */
    public final N target() {
        return target;
    }


    /**
     * Returns the node that is adjacent to {@code node} along the origin edge.
     *
     * @throws IllegalArgumentException if this {@link EndpointPair} does not contain {@code node}
     * @since 20.0 (but the argument type was changed from {@code Object} to {@code N} in 31.0)
     */
    public final N adjacentNode(N node) {
        if (node.equals(source)) {
            return target;
        } else if (node.equals(target)) {
            return source;
        } else {
            throw new IllegalArgumentException("EndpointPair " + this + " does not contain node " + node);
        }
    }


    /**
     * Two ordered {@link EndpointPair}s are equal if their {@link #source()} and {@link #target()}
     * are equal. Two unordered {@link EndpointPair}s are equal if they contain the same nodes. An
     * ordered {@link EndpointPair} is never equal to an unordered {@link EndpointPair}.
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * The hashcode of an ordered {@link EndpointPair} is equal to {@code Objects.hashCode(source(),
     * target())}. The hashcode of an unordered {@link EndpointPair} is equal to {@code
     * nodeU().hashCode() + nodeV().hashCode()}.
     */
    @Override
    public abstract int hashCode();

    private static final class EndpointPairImpl<N> extends EndpointPair<N> {
        private EndpointPairImpl(N source, N target) {
            super(source, target);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof EndpointPair)) {
                return false;
            }

            EndpointPair<?> other = (EndpointPair<?>) obj;

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

}
