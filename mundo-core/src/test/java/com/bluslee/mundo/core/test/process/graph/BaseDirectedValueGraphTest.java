package com.bluslee.mundo.core.test.process.graph;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseDefaultProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author carl.che
 * @date 2021/11/3
 * @description BaseDirectedValueGraphTest
 */
public class BaseDirectedValueGraphTest {

    private BaseDefaultProcessEngine<BaseProcessNode, String> baseDefaultProcessEngine;

    @Before
    public void buildProcess() {
        BaseExecutor baseExecutor = new BaseExecutor(){};
        DirectedValueGraphImpl directedValueGraph = new DirectedValueGraphImpl();
        StartNode startNode = new StartNode("start-node", "开始节点");
        Activity supplierCreate = new Activity("supplier-create", "供应商创建单据");
        Activity supplierSubmit = new Activity("supplier-submit", "供应商提交单据");
        Activity buyerApprove = new Activity("buyer-approve", "采购审批");
        ExclusiveGateway buyerApproveGateway = new ExclusiveGateway("buyer-approve-gateway", "采购审批网关");
        Activity supplierUpdate = new Activity("supplier-update", "供应商修改");
        EndNode approveEnd = new EndNode("approve-end", "审批结束");
        Link startNode2supplierCreate = new Link("start-node2supplier-create", "开始节点2供应商创建单据" , startNode, supplierCreate, "");
        Link supplierCreate2supplierSubmit = new Link("supplier-create2supplier-submit", "供应商创建单据2供应商提交单据" , supplierCreate, supplierSubmit, "");
        Link supplierSubmit2buyerApprove = new Link("supplier-submit2buyer-approve", "供应商提交单据2采购审批" , supplierSubmit, buyerApprove, "");
        Link buyerApprove2buyerApproveGateway = new Link("buyer-approve2buyer-approve-gateway", "采购审批2采购审批网关" , buyerApprove, buyerApproveGateway, "");
        Link buyerApproveGateway2supplierUpdate = new Link("buyer-approve2supplier-update", "采购审批2供应商修改" , buyerApproveGateway, supplierUpdate, "# approved == false");
        Link buyerApproveGateway2approveEnd = new Link("buyer-approve2approve-end", "采购审批2审批结束" , buyerApproveGateway, approveEnd, "# approved == true");
        directedValueGraph.addNode(startNode);
        directedValueGraph.addNode(supplierCreate);
        directedValueGraph.addNode(supplierSubmit);
        directedValueGraph.addNode(buyerApprove);
        directedValueGraph.addNode(buyerApproveGateway);
        directedValueGraph.addNode(supplierUpdate);
        directedValueGraph.addNode(approveEnd);
        directedValueGraph.putEdgeValue(startNode2supplierCreate.getSource(), startNode2supplierCreate.getTarget(), startNode2supplierCreate.getConditionExpression());
        directedValueGraph.putEdgeValue(supplierCreate2supplierSubmit.getSource(), supplierCreate2supplierSubmit.getTarget(), supplierCreate2supplierSubmit.getConditionExpression());
        directedValueGraph.putEdgeValue(supplierSubmit2buyerApprove.getSource(), supplierSubmit2buyerApprove.getTarget(), supplierSubmit2buyerApprove.getConditionExpression());
        directedValueGraph.putEdgeValue(buyerApprove2buyerApproveGateway.getSource(), buyerApprove2buyerApproveGateway.getTarget(), buyerApprove2buyerApproveGateway.getConditionExpression());
        directedValueGraph.putEdgeValue(buyerApproveGateway2supplierUpdate.getSource(), buyerApproveGateway2supplierUpdate.getTarget(), buyerApproveGateway2supplierUpdate.getConditionExpression());
        directedValueGraph.putEdgeValue(buyerApproveGateway2approveEnd.getSource(), buyerApproveGateway2approveEnd.getTarget(), buyerApproveGateway2approveEnd.getConditionExpression());
        baseDefaultProcessEngine = new BaseDefaultProcessEngine<BaseProcessNode, String>("test-process", baseExecutor, directedValueGraph) {};
    }

    @Test
    public void test(){
        String startNodeId = "start-node";
        BaseProcessNode startNode = baseDefaultProcessEngine.getProcessNode(startNodeId);
        Assert.assertThat(startNode.getId(), Matchers.is(startNodeId));
        ProcessNodeWrap<BaseProcessNode> nextProcessNode = baseDefaultProcessEngine.getNextProcessNode(startNode, Collections.EMPTY_MAP);
        System.out.println(nextProcessNode.get().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("approved", true);
        List<BaseProcessNode> baseProcessNodes = baseDefaultProcessEngine.forecastProcessNode(startNode, map);
        baseProcessNodes.forEach(node -> System.out.println(node.toString()));

    }
}
