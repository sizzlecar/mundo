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

    @Test
    public void unorderedTest() {
        Activity activity1 = ProcessElementBuilder.instance("activity1").name("activity1").activity();
        Activity activity2 = ProcessElementBuilder.instance("activity2").name("activity2").activity();
        Edge<BaseProcessNode> edge = Edge.unordered(activity1, activity2);
        Assert.assertFalse(edge.isOrdered());
    }

    @Test
    public void orderedTest() {
        Activity activity1 = ProcessElementBuilder.instance("activity1").name("activity1").activity();
        Activity activity2 = ProcessElementBuilder.instance("activity2").name("activity2").activity();
        Edge<BaseProcessNode> edge = Edge.ordered(activity1, activity2);
        Assert.assertTrue(edge.isOrdered());
    }

    @Test
    public void adjacentNodeTest() {
        Activity activity1 = ProcessElementBuilder.instance("activity1").name("activity1").activity();
        Activity activity2 = ProcessElementBuilder.instance("activity2").name("activity2").activity();
        Edge<BaseProcessNode> edge = Edge.ordered(activity1, activity2);
        BaseProcessNode baseProcessNode = edge.adjacentNode(activity1);
        Assert.assertEquals(baseProcessNode, activity2);
    }

    @Test
    public void unorderedEqualsTest() {
        Activity activity1 = ProcessElementBuilder.instance("activity1").name("activity1").activity();
        Activity activity2 = ProcessElementBuilder.instance("activity2").name("activity2").activity();
        Edge<BaseProcessNode> edge = Edge.unordered(activity1, activity2);
        Activity activity3 = ProcessElementBuilder.instance("activity3").name("activity3").activity();
        Activity activity4 = ProcessElementBuilder.instance("activity4").name("activity4").activity();
        Edge<BaseProcessNode> edge2 = Edge.ordered(activity3, activity4);
        Assert.assertNotEquals(edge, edge2);
        Assert.assertNotEquals(edge, null);
        Assert.assertNotEquals(edge, new Object());
        Assert.assertEquals(edge, edge);
    }
}
