package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;

import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/5
 * @description ParallelGateway
 */
public class ParallelGateway extends BaseParallelGateway {

    public ParallelGateway(String id, String name) {
        super(id, name);
    }

    public ParallelGateway() {
    }

    @Override
    public <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(MutableValueGraph<N, V> processGraph, Map<String, Object> parameterMap, Execute execute) {
        boolean contains = processGraph.nodes().contains(this);
        if (!contains) {
            throw new MundoException("current node is not in process");
        }
        Set<N> successors = processGraph.successors((N) this);
        if (successors == null || successors.size() < 1) {
            throw new MundoException("current node not successors");
        }
        return ProcessNodeWrap.parallel(successors);
    }


}
