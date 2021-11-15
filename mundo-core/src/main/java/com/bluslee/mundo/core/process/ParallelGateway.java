package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseParallelGateway;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.MutableValueGraph;
import java.util.Map;
import java.util.Set;

/**
 * 并行网关.
 *
 * @author carl.che
 */
public class ParallelGateway extends BaseParallelGateway {

    ParallelGateway(final String id, final String name) {
        super(id, name);
    }

    ParallelGateway() {
    }

    @Override
    public final <N extends BaseProcessNode, V> ProcessNodeWrap<N> next(final MutableValueGraph<N, V> processGraph, final Map<String, Object> parameterMap, final Execute execute) {
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
