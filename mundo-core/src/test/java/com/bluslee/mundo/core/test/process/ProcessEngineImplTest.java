package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.ProcessEngineImpl;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * ProcessEngineImplTest.
 * @author carl.che
 */
public class ProcessEngineImplTest {

    @Test
    public void equalsTest() {
        ProcessEngine<BaseProcessNode> processEngine1 = buildProcess("test-01", 0);
        ProcessEngine<BaseProcessNode> processEngine2 = buildProcess("test-01", 0);
        ProcessEngine<BaseProcessNode> processEngine3 = buildProcess("test-01", 2);
        ProcessEngine<BaseProcessNode> processEngine4 = buildProcess("test-02", 2);
        Assert.assertEquals(processEngine1, processEngine2);
        Assert.assertNotEquals(processEngine1, processEngine3);
        Assert.assertNotEquals(processEngine1, processEngine4);
    }

    private ProcessEngine<BaseProcessNode> buildProcess(final String id, final int version) {
        Execute execute = new BaseExecutor() {
        };
        BaseDirectedValueGraph<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        return new ProcessEngineImpl<>(execute, directedValueGraph, id, version);
    }

}
