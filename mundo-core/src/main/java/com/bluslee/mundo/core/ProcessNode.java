package com.bluslee.mundo.core;

import com.bluslee.mundo.core.graph.BaseGraph;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description ProcessNode
 */
public class ProcessNode extends BaseGraph.BaseNode {


    public ProcessNode(String id) {
        super(id);
    }

    @Override
    public void addEdge(String conditionExpress, BaseGraph.BaseNode targetNode) {

    }
}
