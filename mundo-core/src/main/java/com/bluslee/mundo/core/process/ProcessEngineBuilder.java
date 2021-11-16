package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

/**
 * ProcessEngineBuilder.
 *
 * @author carl.che
 */
public final class ProcessEngineBuilder<N extends BaseProcessNode, V> {

    private final String id;

    private Execute execute;

    private BaseDirectedValueGraph<N, V> baseDirectedValueGraph;

    private Integer version = 0;

    private ProcessEngineBuilder(final String id) {
        this.id = id;
    }

    public static <N extends BaseProcessNode, V> ProcessEngineBuilder<N, V> instance(final String id) {
        return new ProcessEngineBuilder<>(id);
    }

    public ProcessEngineBuilder<N, V> execute(final Execute execute) {
        this.execute = execute;
        return this;
    }

    public ProcessEngineBuilder<N, V> directedValueGraph(final BaseDirectedValueGraph<N, V> directedValueGraph) {
        this.baseDirectedValueGraph = directedValueGraph;
        return this;
    }

    public ProcessEngineBuilder<N, V> version(final Integer version) {
        this.version = version;
        return this;
    }

    public ProcessEngineImpl<N, V> build() {
        return new ProcessEngineImpl<>(execute, baseDirectedValueGraph, id, version);
    }
}
