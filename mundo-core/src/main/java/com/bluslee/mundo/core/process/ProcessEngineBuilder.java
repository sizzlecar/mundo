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

    public static ProcessEngineBuilder instance(String id){
        return new ProcessEngineBuilder(id);
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

    public ProcessEngine<N, V> build() {
        ProcessEngine<N, V> processEngine = new ProcessEngine<>(id, execute, baseDirectedValueGraph);
        processEngine.setVersion(version);
        return processEngine;
    }
}
