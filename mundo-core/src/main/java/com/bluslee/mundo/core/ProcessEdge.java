package com.bluslee.mundo.core;

import com.bluslee.mundo.core.graph.BaseGraph;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description Edge
 */
public class ProcessEdge extends BaseGraph.BaseEdge {

    private String conditionExpress;


    public String getConditionExpress() {
        return conditionExpress;
    }

    public void setConditionExpress(String conditionExpress) {
        this.conditionExpress = conditionExpress;
    }
}
