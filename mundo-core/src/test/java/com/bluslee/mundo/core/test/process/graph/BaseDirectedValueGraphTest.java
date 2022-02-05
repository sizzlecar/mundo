package com.bluslee.mundo.core.test.process.graph;

import com.bluslee.mundo.core.process.Activity;
import com.bluslee.mundo.core.process.Link;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.core.process.graph.Edge;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * BaseDirectedValueGraphTest.
 * @author carl.che
 */
public class BaseDirectedValueGraphTest {

    private final DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();

    private final Map<String, BaseProcessNode> processNodeMap = new HashMap<String, BaseProcessNode>() {{
            put("start-node", ProcessElementBuilder.instance("start-node").name("开始节点").startNode());
            put("supplier-create", ProcessElementBuilder.instance("supplier-create").name("供应商创建单据").activity());
            put("supplier-submit", ProcessElementBuilder.instance("supplier-submit").name("供应商提交单据").activity());
            put("buyer-approve", ProcessElementBuilder.instance("buyer-approve").name("采购审批").activity());
            put("buyer-approve-gateway", ProcessElementBuilder.instance("buyer-approve-gateway").name("采购审批网关").exclusiveGateway());
            put("supplier-update", ProcessElementBuilder.instance("supplier-update").name("供应商修改").activity());
            put("approve-end", ProcessElementBuilder.instance("approve-end").name("审批结束").endNode());
        }};

    private final List<Link> processLinkList = new ArrayList<Link>() {{
            add(ProcessElementBuilder.instance("start-node2supplier-create").name("开始节点2供应商创建单据").source(processNodeMap.get("start-node"))
                    .target(processNodeMap.get("supplier-create")).conditionExpression("").link());
            add(ProcessElementBuilder.instance("supplier-create2supplier-submit").name("供应商创建单据2供应商提交单据").source(processNodeMap.get("supplier-create"))
                    .target(processNodeMap.get("supplier-submit")).conditionExpression("").link());
            add(ProcessElementBuilder.instance("supplier-submit2buyer-approve").name("供应商提交单据2采购审批").source(processNodeMap.get("supplier-submit"))
                    .target(processNodeMap.get("buyer-approve")).conditionExpression("").link());
            add(ProcessElementBuilder.instance("supplier-update2buyer-approve").name("供应商修改2采购审批").source(processNodeMap.get("supplier-update"))
                    .target(processNodeMap.get("buyer-approve")).conditionExpression("").link());
            add(ProcessElementBuilder.instance("buyer-approve2buyer-approve-gateway").name("采购审批2采购审批网关").source(processNodeMap.get("buyer-approve"))
                    .target(processNodeMap.get("buyer-approve-gateway")).conditionExpression("").link());
            add(ProcessElementBuilder.instance("buyer-approve-gateway2supplier-update").name("采购审批网关2供应商修改").source(processNodeMap.get("buyer-approve-gateway"))
                    .target(processNodeMap.get("supplier-update")).conditionExpression("# approved == false").link());
            add(ProcessElementBuilder.instance("buyer-approve-gateway2approve-end").name("采购审批网关2审批结束").source(processNodeMap.get("buyer-approve-gateway"))
                    .target(processNodeMap.get("approve-end")).conditionExpression("# approved == true").link());
        }};

    @Before
    public void buildGraph() {
        processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
        processLinkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression()));
    }

    @Test
    public void nodeSizeTest() {
        MatcherAssert.assertThat(directedValueGraph.nodes().size(), Matchers.is(processNodeMap.size()));
    }

    @Test
    public void edgeCountTest() {
        MatcherAssert.assertThat(directedValueGraph.edges().size(), Matchers.is(processLinkList.size()));
    }

    @Test
    public void relationTest() {
        processLinkList.forEach(link -> {
            Assert.assertTrue(directedValueGraph.hasEdgeConnecting(link.getSource(), link.getTarget()));
            String conditionExpression = link.getConditionExpression();
            if (conditionExpression != null && conditionExpression.trim().length() > 1) {
                Optional<String> edgeValueOpt = directedValueGraph.edgeValue(link.getSource(), link.getTarget());
                Assert.assertTrue(edgeValueOpt.isPresent());
                MatcherAssert.assertThat(conditionExpression, Matchers.is(edgeValueOpt.get()));
            }
        });
    }

    @Test
    public void putEdgeValueTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        String val = "test";
        directedValueGraph.putEdgeValue(ordered, val);
        Assert.assertTrue(directedValueGraph.hasEdgeConnecting(ordered));
        Assert.assertEquals(directedValueGraph.edgeValueOrDefault(ordered, null), val);
    }

    @Test
    public void removeNodeTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        directedValueGraph.addNode(node1);
        Assert.assertTrue(directedValueGraph.nodes().contains(node1));
        directedValueGraph.removeNode(node1);
        Assert.assertFalse(directedValueGraph.nodes().contains(node1));
    }

    @Test
    public void removeEdge() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        String val = "test";
        directedValueGraph.putEdgeValue(ordered, val);
        Assert.assertTrue(directedValueGraph.hasEdgeConnecting(ordered));
        directedValueGraph.removeEdge(ordered);
        Assert.assertFalse(directedValueGraph.hasEdgeConnecting(ordered));
    }

    @Test
    public void removeEdge2() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        String val = "test";
        directedValueGraph.putEdgeValue(ordered, val);
        Assert.assertTrue(directedValueGraph.hasEdgeConnecting(ordered));
        directedValueGraph.removeEdge(node1, node2);
        Assert.assertFalse(directedValueGraph.hasEdgeConnecting(ordered));
    }

    @Test
    public void edgesTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Assert.assertEquals(directedValueGraph.edges().size(), 0);
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Assert.assertEquals(directedValueGraph.edges(), Collections.singleton(ordered));
    }

    @Test
    public void isDirectedTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Assert.assertTrue(directedValueGraph.isDirected());
    }

    @Test
    public void allowsSelfLoopsTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Assert.assertTrue(directedValueGraph.allowsSelfLoops());
    }

    @Test
    public void adjacentNodesTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Set<BaseProcessNode> adjacentNodes = directedValueGraph.adjacentNodes(node1);
        Assert.assertEquals(Collections.singleton(node2), adjacentNodes);
    }

    @Test
    public void predecessorsTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Set<BaseProcessNode> node1Predecessors = directedValueGraph.predecessors(node1);
        Set<BaseProcessNode> node2Predecessors = directedValueGraph.predecessors(node2);
        Assert.assertEquals(Collections.singleton(node1), node2Predecessors);
        Assert.assertEquals(0, node1Predecessors.size());
    }

    @Test
    public void degreeTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Assert.assertEquals(1, directedValueGraph.degree(node1));
        Assert.assertEquals(1, directedValueGraph.degree(node2));
    }

    @Test
    public void inDegreeTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Assert.assertEquals(0, directedValueGraph.inDegree(node1));
        Assert.assertEquals(1, directedValueGraph.inDegree(node2));
    }

    @Test
    public void outDegreeTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Assert.assertEquals(1, directedValueGraph.outDegree(node1));
        Assert.assertEquals(0, directedValueGraph.outDegree(node2));
    }

    @Test
    public void incomingEdgesTest() {
        DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
        Activity node1 = ProcessElementBuilder.instance("node1").activity();
        Activity node2 = ProcessElementBuilder.instance("node2").activity();
        Edge<BaseProcessNode> ordered = Edge.ordered(node1, node2);
        directedValueGraph.putEdgeValue(ordered, "test");
        Assert.assertEquals(0, directedValueGraph.incomingEdges(node1).size());
        Assert.assertEquals(Collections.singleton(ordered), directedValueGraph.incomingEdges(node2));
    }
}
