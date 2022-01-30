package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.expression.Execute;
import com.bluslee.mundo.core.process.ProcessEngineImpl;
import com.bluslee.mundo.core.process.Repository;
import com.bluslee.mundo.core.process.RepositoryBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.graph.BaseDirectedValueGraph;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;

/**
 * RepositoryBuilderTest.
 * @author carl.che
 */
public class RepositoryBuilderTest {

    @Test
    public void buildTest() {
        Set<ProcessEngine<BaseProcessNode>> processEngines = new HashSet<>();
        processEngines.add(buildProcess("test-01", 0));
        processEngines.add(buildProcess("test-02", 1));
        Repository<BaseProcessNode> repository = RepositoryBuilder.build(processEngines);
        Assert.assertEquals(processEngines, repository.processes());
    }

    private ProcessEngine<BaseProcessNode> buildProcess(final String id, final int version) {
        Execute execute = new BaseExecutor() {
        };
        BaseDirectedValueGraph<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        return new ProcessEngineImpl<>(execute, directedValueGraph, id, version);
    }
}
