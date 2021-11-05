package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;

import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/4
 * @description ProcessNodeWrap ProcessNode 包装类
 */
public abstract class ProcessNodeWrap<N> {

    private final N node;
    private final Set<N> parallelNodes;

    private ProcessNodeWrap(N node, Set<N> parallelNodes) {
        this.node = node;
        this.parallelNodes = parallelNodes;
    }

    /**
     * 当前节点是否是并行节点
     *
     * @return true 是，false 否
     */
    public abstract boolean parallel();

    /**
     * 非并行节点获取当前节点
     * 如果是并行节点则抛出异常
     *
     * @return 当前节点
     */
    public N get() {
        return node;
    }

    /**
     * 并行节点获取当前所有并行节点
     * 如果是非并行节点则抛出异常
     *
     * @return
     */
    public Set<N> getParallelNodes() {
        return parallelNodes;
    }

    public static <N> ProcessNodeWrap<N> parallel(Set<N> parallelNodes) {
        return new ParallelNode<>(parallelNodes);
    }

    public static <N> ProcessNodeWrap<N> unParallel(N node) {
        return new UnParallelNode<>(node);
    }

    public static class ParallelNode<N> extends ProcessNodeWrap<N> {

        private ParallelNode(Set<N> parallelNodes) {
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

    public static class UnParallelNode<N> extends ProcessNodeWrap<N> {

        private UnParallelNode(N node) {
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
