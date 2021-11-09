package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseActivity;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author carl.che
 * @date 2021/11/1
 * @description Activity
 */
public class Activity extends BaseActivity {

    Activity(String id, String name) {
        super(id, name);
    }

    Activity() {
    }

    @Override
    public <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute) {
        boolean contains = processGraph.nodes().contains(this);
        if (!contains) {
            throw new MundoException("当前节点不在指定图中");
        }
        Set<N> successors = processGraph.successors((N) this);
        if (successors == null || successors.size() < 1) {
            throw new MundoException("当前节点没有后续节点");
        }
        List<N> singleList = new ArrayList<>(successors);
        N nextNode = singleList.get(0);
        if (nextNode instanceof BaseActivity) {
            //下一个节点是活动节点直接返回
            return ProcessNodeWrap.unParallel(nextNode);
        } else {
            return nextNode.next(processGraph, parameterMap, execute);
        }
    }
}
