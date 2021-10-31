package com.bluslee.mundo.core.graph;

import java.util.Set;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description BaseGraph
 */
public abstract class BaseGraph {

    /**
     * 初始节点
     */
    protected BaseNode root;

    public BaseGraph() {

    }

    public BaseGraph(BaseNode root) {
        this.root = root;
    }

    /**
     * 给当前的图的指定的节点添加一个边
     *
     * @param value      边
     * @param toBaseNode -> node
     */
    public abstract void addEdge(String value, BaseNode fromBaseNode, BaseNode toBaseNode);


    public BaseNode getRoot() {
        return root;
    }

    /**
     * 图的节点，由 类型，id, 由该节点出发的边构成
     */
    public static abstract class BaseNode {
        protected String id;
        //key -> 节点的边， val -> 边指向的节点
        protected Set<BaseEdge> edges;

        public BaseNode() {
        }

        public BaseNode(String id) {
            this.id = id;
        }

        /**
         * 给当前节点添加一个边
         *
         * @param conditionExpress 边
         * @param targetNode       -> node
         */
        public abstract void addEdge(String conditionExpress, BaseNode targetNode);

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Set<BaseEdge> getEdges() {
            return edges;
        }

        public void setEdges(Set<BaseEdge> edges) {
            this.edges = edges;
        }
    }

    /**
     * 图的边，由开始节点，结束节点，边的条件组成
     */
    public static abstract class BaseEdge {
        protected String id;
        protected BaseNode source;
        protected BaseNode target;

        public BaseEdge(String id, BaseNode source, BaseNode target) {
            this.id = id;
            this.source = source;
            this.target = target;
        }

        public BaseEdge() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public BaseNode getSource() {
            return source;
        }

        public void setSource(BaseNode source) {
            this.source = source;
        }

        public BaseNode getTarget() {
            return target;
        }

        public void setTarget(BaseNode target) {
            this.target = target;
        }
    }
}
