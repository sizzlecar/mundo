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
     * 根据node，parallelNodes判断两个对象是否相等.
     *
     * @param o 带比较的对象
     * @return true 相等，false 不相等
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessNodeWrap<?> that = (ProcessNodeWrap<?>) o;
        return Objects.equals(node, that.node) && Objects.equals(parallelNodes, that.parallelNodes);
    }

    /**
     * 根据node, parallelNodes生成hashcode.
     *
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(node, parallelNodes);
    }

    /**
     * 根据node，parallelNodes生成toString.
     *
     * @return toString
     */
    @Override
    public String toString() {
        return "ProcessNodeWrap{"
                +
                "node="
                + node
                +
                ", parallelNodes=" + parallelNodes
                +
                '}';
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
    }

}
