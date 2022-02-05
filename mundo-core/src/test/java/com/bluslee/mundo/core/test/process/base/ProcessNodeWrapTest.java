package com.bluslee.mundo.core.test.process.base;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;

/**
 * ProcessNodeWrapTest.
 *
 * @author carl.che
 */
public class ProcessNodeWrapTest {

    @Test
    public void test() {
        ProcessNodeWrap<BaseProcessNode> processNodeWrap = ProcessNodeWrap
                .unParallel(ProcessElementBuilder.instance("node").activity());
        ProcessNodeWrap<BaseProcessNode> processNodeWrap2 = ProcessNodeWrap
                .unParallel(ProcessElementBuilder.instance("node2").activity());
        Assert.assertTrue(processNodeWrap.equals(processNodeWrap));
        Assert.assertFalse(processNodeWrap.equals(processNodeWrap2));
        Assert.assertFalse(processNodeWrap.equals(new Object()));
        Assert.assertFalse(processNodeWrap.equals(null));
        Assert.assertThrows(MundoException.class, processNodeWrap::getParallelNodes);
        Assert.assertNotEquals(processNodeWrap.hashCode(), processNodeWrap2.hashCode());
    }

    @Test
    public void test2() {
        ProcessNodeWrap<BaseProcessNode> processNodeWrap = ProcessNodeWrap
                .parallel(Collections.singleton(ProcessElementBuilder.instance("node1").activity()));
        ProcessNodeWrap<BaseProcessNode> processNodeWrap2 = ProcessNodeWrap
                .parallel(Collections.singleton(ProcessElementBuilder.instance("node2").activity()));
        Assert.assertTrue(processNodeWrap.equals(processNodeWrap));
        Assert.assertFalse(processNodeWrap.equals(processNodeWrap2));
        Assert.assertFalse(processNodeWrap.equals(new Object()));
        Assert.assertFalse(processNodeWrap.equals(null));
        Assert.assertThrows(MundoException.class, processNodeWrap::get);
        Assert.assertNotEquals(processNodeWrap.hashCode(), processNodeWrap2.hashCode());
    }
}
