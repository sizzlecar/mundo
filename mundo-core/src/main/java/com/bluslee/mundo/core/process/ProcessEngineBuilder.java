package com.bluslee.mundo.core.process;

import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;

/**
 * @author carl.che
 * @date 2021/11/8
 * @description ProcessEngineBuilder
 */
public class ProcessEngineBuilder<N extends BaseProcessNode, V> {

    private final String id;
    private Execute execute;
    private BaseDirectedValueGraph<N, V> baseDirectedValueGraph;
    private int version;

    private ProcessEngineBuilder(String id) {
        this.id = id;
    }

    public static <N extends BaseProcessNode, V> ProcessEngineBuilder<N, V> instance(String id){
        return new ProcessEngineBuilder<>(id);
    }

    public ProcessEngineBuilder<N, V> execute(Execute execute){
        this.execute = execute;
        return this;
    }

    public ProcessEngineBuilder<N, V> directedValueGraph(BaseDirectedValueGraph<N, V> directedValueGraph){
        this.baseDirectedValueGraph = directedValueGraph;
        return this;
    }

    public ProcessEngineBuilder<N, V> version(int version){
        this.version = version;
        return this;
    }

    public ProcessEngineImpl<N, V> build() {
        ProcessEngineImpl<N, V> processEngineImpl = new ProcessEngineImpl<>(id, execute, baseDirectedValueGraph);
        processEngineImpl.setVersion(version);
        return processEngineImpl;
    }
}
