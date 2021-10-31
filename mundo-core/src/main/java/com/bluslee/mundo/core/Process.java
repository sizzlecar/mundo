package com.bluslee.mundo.core;

import com.bluslee.mundo.core.graph.BaseGraph;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description ProcessGraph
 */
public class Process extends BaseGraph {
    public Process(BaseNode root) {
        super(root);
    }

    @Override
    public void addEdge(String conditionExpress, BaseNode fromBaseNode, BaseNode toBaseNode) {

    }
}
