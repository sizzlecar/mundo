package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;

import java.util.Map;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description EndNode
 */
public class EndNode extends BaseProcessNode{

    public EndNode(String id, String name) {
        super(id, name);
    }

    public EndNode() {
    }

    @Override
    public <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute) {
        return ProcessNodeWrap.unParallel((N)this);
    }
}
