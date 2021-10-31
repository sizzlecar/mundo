package com.bluslee.mundo.core;

import com.bluslee.mundo.core.graph.BaseGraph;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description BaseGatewayNode that may reach multiple nodes
 */
public abstract class BaseGatewayNode extends BaseGraph.BaseNode {

    public BaseGatewayNode() {
    }

    public BaseGatewayNode(String id) {
        super(id);
    }

    @Override
    public void addEdge(String conditionExpress, BaseGraph.BaseNode targetNode) {

    }
}
