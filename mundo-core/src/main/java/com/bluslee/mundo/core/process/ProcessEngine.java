package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description ProcessEngine
 */
public class ProcessEngine<N extends BaseProcessNode, V> extends BaseProcessEngine<N, V> {

    ProcessEngine(String id, Execute execute, BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
        super(id, execute, baseDirectedValueGraph);
    }
}
