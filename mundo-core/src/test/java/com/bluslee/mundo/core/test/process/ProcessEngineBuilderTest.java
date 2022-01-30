package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.ProcessEngineBuilder;
import com.bluslee.mundo.core.process.ProcessEngineImpl;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * ProcessEngineBuilderTest.
 *
 * @author carl.che
 */
public class ProcessEngineBuilderTest {

    @Test
    public void buildTest() {
        Execute execute = new BaseExecutor() {
        };
        BaseDirectedValueGraph<BaseProcessNode, Object> directedValueGraph = new DirectedValueGraphImpl<>();
        ProcessEngineImpl<BaseProcessNode, Object> processEngine = ProcessEngineBuilder
                .instance("test")
                .version(1)
                .execute(execute)
                .directedValueGraph(directedValueGraph)
                .build();
        ProcessEngine<BaseProcessNode> processEngine1 = buildProcess("test", 1);
        Assert.assertEquals(processEngine1, processEngine);
    }

    private ProcessEngine<BaseProcessNode> buildProcess(final String id, final int version) {
        Execute execute = new BaseExecutor() {
        };
        BaseDirectedValueGraph<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        return new ProcessEngineImpl<>(execute, directedValueGraph, id, version);
    }
}
