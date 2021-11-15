package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseExclusiveGateway;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.Edge;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 流程基础元素排他网关.
 *
 * @author carl.che
 */
public class ExclusiveGateway extends BaseExclusiveGateway {

    ExclusiveGateway(final String id, final String name) {
        super(id, name);
    }

    ExclusiveGateway() {
    }

    @Override
    public final <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(final MutableValueGraph<N, V> processGraph, final Map<String, Object> parameterMap, final Execute execute) {
        boolean contains = processGraph.nodes().contains(this);
        if (!contains) {
            throw new MundoException("current node not in processGraph");
        }
        Set<Edge<N>> outgoingEdges = processGraph.outgoingEdges((N) this);
        Optional<Edge<N>> matchPair = outgoingEdges
                .stream().filter(pair -> {
                    Optional<V> edgeValueOpt = processGraph.edgeValue(pair);
                    if (!edgeValueOpt.isPresent()) {
                        throw new MundoException("edge val is null");
                    }
                    String expression = edgeValueOpt.get().toString();
                    //解析表达式，根据map进行计算
                    return execute.executeExpression(expression, parameterMap);
                }).findFirst();
        if (!matchPair.isPresent()) {
            throw new MundoException("no match next node");
        }
        return ProcessNodeWrap.unParallel(matchPair.get().target());
    }
}
