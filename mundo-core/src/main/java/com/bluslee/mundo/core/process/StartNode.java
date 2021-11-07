package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description StartNode
 */
public class StartNode extends BaseProcessNode {

    public StartNode(String id, String name) {
        super(id, name);
    }

    public StartNode() {
    }

    @Override
    public <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute) {
        boolean contains = processGraph.nodes().contains(this);
        if (!contains) {
            throw new MundoException("当前节点不在指定图中");
        }
        Set<N> successors = processGraph.successors((N)this);
        if (successors == null || successors.size() < 1) {
            throw new MundoException("当前节点没有后续节点");
        }
        List<N> singleList = new ArrayList<>(successors);
        return ProcessNodeWrap.unParallel(singleList.get(0));
    }
}
