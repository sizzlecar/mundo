package com.bluslee.mundo.core.process.base;

import com.bluslee.mundo.core.exception.MundoException;
import java.util.Objects;
import java.util.Set;

/**
 * ProcessNode 包装类,为了描述某些场景下出现的并行节点.
 *
 * @author carl.che
 */
public abstract class ProcessNodeWrap<N> {

    private final N node;

    private final Set<N> parallelNodes;

    private ProcessNodeWrap(final N node, final Set<N> parallelNodes) {
        this.node = node;
        this.parallelNodes = parallelNodes;
    }

    /**
     * 非并行节点获取当前节点, 如果是并行节点则抛出异常.
     *
     * @return 当前节点
     */
    public N get() {
        return node;
    }

    /**
     * 并行节点获取当前所有并行节点
     * 如果是非并行节点则抛出异常.
     *
     * @return 所有的并行节点
     */
    public Set<N> getParallelNodes() {
        return parallelNodes;
    }

    /**
     * 当前节点是否是并行节点.
     *
     * @return true 是，false 否
     */
    public abstract boolean parallel();

    public static <N> ProcessNodeWrap<N> parallel(final Set<N> parallelNodes) {
        return new ParallelNode<>(parallelNodes);
    }

    public static <N> ProcessNodeWrap<N> unParallel(final N node) {
        return new UnParallelNode<>(node);
    }

    public static final class ParallelNode<N> extends ProcessNodeWrap<N> {

        private ParallelNode(final Set<N> parallelNodes) {
            super(null, parallelNodes);
        }

        @Override
        public boolean parallel() {
            return true;
        }

        @Override
        public N get() {
            throw new MundoException("ParallelNode not support get");
        }

        @Override
        public Set<N> getParallelNodes() {
            return super.getParallelNodes();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getParallelNodes());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ParallelNode<?> that = (ParallelNode<?>) o;
            return Objects.equals(getParallelNodes(), that.getParallelNodes());

        }
    }

    public static final class UnParallelNode<N> extends ProcessNodeWrap<N> {

        private UnParallelNode(final N node) {
            super(node, null);
        }

        @Override
        public boolean parallel() {
            return false;
        }

        @Override
        public N get() {
            return super.get();
        }

        @Override
        public Set<N> getParallelNodes() {
            throw new MundoException("UnParallelNode not support getParallelNodes");
        }

        @Override
        public int hashCode() {
            return Objects.hash(get());
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            UnParallelNode<?> that = (UnParallelNode<?>) o;
            return Objects.equals(get(), that.get());
        }
    }

}
