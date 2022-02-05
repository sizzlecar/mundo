package com.bluslee.mundo.core.test.process.graph;

import com.bluslee.mundo.core.process.Activity;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.Edge;
import org.junit.Assert;
import org.junit.Test;

/**
 * EdgeTest.
 *
 * @author carl.che
 */
public class EdgeTest {

    private final Activity activity1 = ProcessElementBuilder.instance("activity1").name("activity1").activity();

    private final Activity activity2 = ProcessElementBuilder.instance("activity2").name("activity2").activity();

    private final Edge<BaseProcessNode> unordered = Edge.unordered(activity1, activity2);

    private final Activity activity3 = ProcessElementBuilder.instance("activity3").name("activity3").activity();

    private final Activity activity4 = ProcessElementBuilder.instance("activity4").name("activity4").activity();

    private final Edge<BaseProcessNode> ordered = Edge.ordered(activity3, activity4);

    @Test
    public void unorderedTest() {
        Assert.assertFalse(unordered.isOrdered());
    }

    @Test
    public void orderedTest() {
        Assert.assertTrue(ordered.isOrdered());
    }

    @Test
    public void adjacentNodeTest() {
        Assert.assertEquals(activity4, ordered.adjacentNode(activity3));
        Assert.assertEquals(activity3, ordered.adjacentNode(activity4));
        Assert.assertThrows(IllegalArgumentException.class, () -> ordered.adjacentNode(activity1));
    }

    @Test
    public void unorderedEqualsTest() {
        Assert.assertNotEquals(unordered, ordered);
        Assert.assertNotEquals(unordered, null);
        Assert.assertNotEquals(unordered, new Object());
        Assert.assertEquals(unordered, unordered);
    }

    @Test
    public void orderedEqualsTest() {
        Assert.assertNotEquals(ordered, unordered);
        Assert.assertNotEquals(ordered, null);
        Assert.assertNotEquals(ordered, new Object());
        Assert.assertEquals(ordered, ordered);
    }

    @Test
    public void orderHashcodeTest() {
        Assert.assertNotEquals(ordered.hashCode(), unordered.hashCode());
    }

    @Test
    public void unorderedTest2() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> unordered.source());
        Assert.assertThrows(UnsupportedOperationException.class, () -> unordered.target());
    }

    //just for coverage
    @Test
    public void toStringTest() {
        ordered.toString();
        unordered.toString();
    }

}
