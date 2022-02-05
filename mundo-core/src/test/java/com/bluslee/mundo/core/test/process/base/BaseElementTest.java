package com.bluslee.mundo.core.test.process.base;

import com.bluslee.mundo.core.process.base.BaseElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseElementTest.
 *
 * @author carl.che
 */
public class BaseElementTest {

    @Test
    public void equalsTest() {
        BaseElement baseElement1 = new BaseElement("el1", "el1") {
        };
        BaseElement baseElement2 = new BaseElement("el1", "el2") {
        };
        BaseElement baseElement3 = new BaseElement("el2", "el2") {
        };
        Assert.assertEquals(baseElement1, baseElement2);
        Assert.assertNotEquals(baseElement1, baseElement3);
        Assert.assertNotEquals(baseElement1, new Object());
    }
}
