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
import java.util.Optional;

/**
 * 流程基础元素 Activity.
 *
 * @author carl.che
 */
public class Activity extends BaseActivity {

    Activity(final String id, final String name) {
        super(id, name);
    }

    Activity() {
    }

    @Override
    public final <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(final MutableValueGraph<N, V> processGraph, final Map<String, Object> parameterMap, final Execute execute) {
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
        Optional<V> expressionOpt = processGraph.edgeValue((N) this, nextNode);
        boolean expressFlag = expressionOpt.isPresent() && expressionOpt.get().toString().trim().length() > 1;
        boolean baseActivityFlag = nextNode instanceof BaseActivity;
        if (expressFlag) {
            //有条件
            boolean flag = execute.executeExpression(expressionOpt.get(), parameterMap);
            if (!flag) {
                return nextNode.next(processGraph, parameterMap, execute);
            }
        }
        return baseActivityFlag ? ProcessNodeWrap.unParallel(nextNode) : nextNode.next(processGraph, parameterMap, execute);
    }
}
