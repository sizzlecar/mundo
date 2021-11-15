package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;
import java.util.Map;

/**
 * 流程基础元素，结束节点.
 *
 * @author carl.che
 */
public class EndNode extends BaseProcessNode {

    EndNode(final String id, final String name) {
        super(id, name);
    }

    EndNode() {
    }

    @Override
    public final <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(final MutableValueGraph<N, V> processGraph, final Map<String, Object> parameterMap, final Execute execute) {
        return null;
    }
}
