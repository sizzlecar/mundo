package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description BaseDefaultProcessEngineTest
 */
public class BaseDefaultProcessEngineTest {

    private BaseProcessEngine<BaseProcessNode, String> baseDefaultProcessEngine;
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
    public void buildProcess() {
        BaseExecutor baseExecutor = new BaseExecutor() {
        };
        DirectedValueGraphImpl directedValueGraph = new DirectedValueGraphImpl();
        processNodeMap.forEach((id, node) -> {
            directedValueGraph.addNode(node);
        });
        processLinkList.forEach(link -> {
            directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression());
        });
        baseDefaultProcessEngine = new BaseProcessEngine<BaseProcessNode, String>("test-process", baseExecutor, directedValueGraph) {
        };
    }

    @Test
    public void getProcessNodeTest() {
        processNodeMap.forEach((id, node) -> {
            BaseProcessNode processNode = baseDefaultProcessEngine.getProcessNode(id);
            Assert.assertThat(processNode.getId(), Matchers.equalTo(id));
        });
    }

    @Test
    public void getNextProcessNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("approved", true);
        processNodeMap.forEach((id, node) -> {
            ProcessNodeWrap<BaseProcessNode> processNodeWrap = baseDefaultProcessEngine.getNextProcessNode(id, paraMap);
            if (processNodeWrap.parallel()) {
                System.out.println(processNodeWrap.getParallelNodes());
            } else {
                System.out.println(processNodeWrap.get());
            }
        });
    }

    @Test
    public void getNextProcessNodeByNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("approved", true);
        processNodeMap.forEach((id, node) -> {
            ProcessNodeWrap<BaseProcessNode> processNodeWrap = baseDefaultProcessEngine.getNextProcessNode(node, paraMap);
            if (processNodeWrap.parallel()) {
                System.out.println(processNodeWrap.getParallelNodes());
            } else {
                System.out.println(processNodeWrap.get());
            }
        });
    }

    @Test
    public void forecastProcessNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("approved", false);
        processNodeMap.forEach((id, node) -> {
            Set<BaseProcessNode> processNodeList = baseDefaultProcessEngine.forecastProcessNode(node, paraMap);
            System.out.println(processNodeList);
        });
    }
}
