package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description ExclusiveGateway
 */
public class ExclusiveGateway extends BaseExclusiveGateway{

    public ExclusiveGateway(String id, String name) {
        super(id, name);
    }

    public ExclusiveGateway() {
    }

    @Override
    public <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute) {
        boolean contains = processGraph.nodes().contains(this);
        if(!contains){
            throw new MundoException("current node not in processGraph");
        }
        Set<Edge<N>> outgoingEdges = processGraph.outgoingEdges((N)this);
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
        return ProcessNodeWrap.unParalle(matchPair.get().target());
    }
}
