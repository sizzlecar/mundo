package com.bluslee.mundo.core.test.process.graph;

import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description BaseDirectedValueGraphTest
 */
public class BaseDirectedValueGraphTest {

    private DirectedValueGraphImpl directedValueGraph = new DirectedValueGraphImpl();
    private final Map<String, BaseProcessNode> processNodeMap = new HashMap<String, BaseProcessNode>() {{
        put("start-node", new StartNode("start-node", "开始节点"));
        put("supplier-create", new Activity("supplier-create", "供应商创建单据"));
        put("supplier-submit", new Activity("supplier-submit", "供应商提交单据"));
        put("buyer-approve", new Activity("buyer-approve", "采购审批"));
        put("buyer-approve-gateway", new ExclusiveGateway("buyer-approve-gateway", "采购审批网关"));
        put("supplier-update", new Activity("supplier-update", "供应商修改"));
        put("approve-end", new EndNode("approve-end", "审批结束"));
    }};
    private final List<Link> processLinkList = new ArrayList<Link>() {{
        add(new Link("start-node2supplier-create", "开始节点2供应商创建单据", processNodeMap.get("start-node"), processNodeMap.get("supplier-create"), ""));
        add(new Link("supplier-create2supplier-submit", "供应商创建单据2供应商提交单据", processNodeMap.get("supplier-create"), processNodeMap.get("supplier-submit"), ""));
        add(new Link("supplier-submit2buyer-approve", "供应商提交单据2采购审批", processNodeMap.get("supplier-submit"), processNodeMap.get("buyer-approve"), ""));
        add(new Link("supplier-update2buyer-approve", "供应商修改2采购审批", processNodeMap.get("supplier-update"), processNodeMap.get("buyer-approve"), ""));
        add(new Link("buyer-approve2buyer-approve-gateway", "采购审批2采购审批网关", processNodeMap.get("buyer-approve"), processNodeMap.get("buyer-approve-gateway"), ""));
        add(new Link("buyer-approve-gateway2supplier-update", "采购审批网关2供应商修改", processNodeMap.get("buyer-approve-gateway"), processNodeMap.get("supplier-update"), "# approved == false"));
        add(new Link("buyer-approve-gateway2approve-end", "采购审批网关2审批结束", processNodeMap.get("buyer-approve-gateway"), processNodeMap.get("approve-end"), "# approved == true"));
    }};


    @Before
    public void buildGraph() {
        processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
        processLinkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression()));
    }

    @Test
    public void nodeSizeTest() {
        Assert.assertThat(directedValueGraph.nodes().size(), Matchers.is(processNodeMap.size()));
    }

    @Test
    public void edgeCountTest() {
        Assert.assertThat(directedValueGraph.edges().size(), Matchers.is(processLinkList.size()));
    }

    @Test
    public void relationTest() {
        processLinkList.forEach(link -> {
            Assert.assertTrue(directedValueGraph.hasEdgeConnecting(link.getSource(), link.getTarget()));
            String conditionExpression = link.getConditionExpression();
            if (conditionExpression != null && conditionExpression.trim().length() > 1) {
                Optional<String> edgeValueOpt = directedValueGraph.edgeValue(link.getSource(), link.getTarget());
                Assert.assertTrue(edgeValueOpt.isPresent());
                Assert.assertThat(conditionExpression, Matchers.is(edgeValueOpt.get()));
            }
        });
    }
}
