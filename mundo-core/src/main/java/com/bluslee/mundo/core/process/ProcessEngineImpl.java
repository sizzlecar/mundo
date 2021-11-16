package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

/**
 * {@link BaseProcessEngine}默认实现.
 *
 * @author carl.che
 * @see BaseProcessEngine
 */
public class ProcessEngineImpl<N extends BaseProcessNode, V> extends BaseProcessEngine<N, V> {

    ProcessEngineImpl(final String id, final Execute execute, final BaseDirectedValueGraph<N, V> baseDirectedValueGraph) {
        super(id, execute, baseDirectedValueGraph);
    }

    public ProcessEngineImpl(final Execute execute, final BaseDirectedValueGraph<N, V> baseDirectedValueGraph,
                             final String id, final Integer version) {
        super(execute, baseDirectedValueGraph, id, version);
    }

    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }
}
