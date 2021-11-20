package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.Link;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.ParallelGateway;
import com.bluslee.mundo.core.process.ExclusiveGateway;
import com.bluslee.mundo.core.process.base.BaseGateway;
import com.bluslee.mundo.core.process.base.BaseProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.core.process.graph.Edge;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * BaseDefaultProcessEngineImplTest.
 *
 * @author carl.che
 */
public class BaseDefaultProcessEngineImplTest {

    private BaseProcessEngine<BaseProcessNode, String> baseDefaultProcessEngine;

    private final Map<String, BaseProcessNode> processNodeMap = new HashMap<String, BaseProcessNode>() {{
            put("START", ProcessElementBuilder.instance("START").name("开始").startNode());
            put("SUP_CREATE", ProcessElementBuilder.instance("SUP_CREATE").name("供应商新建").activity());
            put("BUYER_APPROVE", ProcessElementBuilder.instance("BUYER_APPROVE").name("采购审批").activity());
            put("SUP_UPDATE", ProcessElementBuilder.instance("SUP_UPDATE").name("供应商修改").activity());
            put("BUYER_MANAGEMENT_APPROVE", ProcessElementBuilder.instance("BUYER_MANAGEMENT_APPROVE").name("采购经理审批").activity());
            put("BUYER_DIRECTOR_APPROVE", ProcessElementBuilder.instance("BUYER_DIRECTOR_APPROVE").name("采购总监审批").activity());
            put("BUYER_MANAGEMENT_BUYER_UPDATE", ProcessElementBuilder.instance("BUYER_MANAGEMENT_BUYER_UPDATE").name("总监驳回采购修改").activity());
            put("BUYER_DIRECTOR_BUYER_UPDATE", ProcessElementBuilder.instance("BUYER_DIRECTOR_BUYER_UPDATE").name("经理驳回采购修改").activity());
            put("MASTER_BUYER_UPDATE", ProcessElementBuilder.instance("MASTER_BUYER_UPDATE").name("主档驳回采购修改").activity());
            put("MASTER_APPROVE", ProcessElementBuilder.instance("MASTER_APPROVE").name("主档审批").activity());
            put("BUYER-APPROVE-GATEWAY", ProcessElementBuilder.instance("BUYER-APPROVE-GATEWAY").name("采购审批网关").exclusiveGateway());
            put("BUYER-MANAGEMENT-GATEWAY", ProcessElementBuilder.instance("BUYER-MANAGEMENT-GATEWAY").name("采购经理审批网关").exclusiveGateway());
            put("BUYER-DIRECTOR-GATEWAY", ProcessElementBuilder.instance("BUYER-DIRECTOR-GATEWAY").name("采购总监审批网关").exclusiveGateway());
            put("MASTER-GATEWAY", ProcessElementBuilder.instance("MASTER-GATEWAY").name("主档审批网关").exclusiveGateway());
            put("PARALLEL-GATEWAY", ProcessElementBuilder.instance("PARALLEL-GATEWAY").name("并行审批网关").parallelGateway());
            put("END", ProcessElementBuilder.instance("END").name("审批结束").endNode());
        }};

    private final List<Link> processLinkList = new ArrayList<Link>();

    private final DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();

    private final BaseExecutor baseExecutor = new BaseExecutor() {
    };

    @Before
    public void buildProcess() {
        processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
        processLinkList.add(ProcessElementBuilder.instance("START->SUP_CREATE").source(processNodeMap.get("START")).target(processNodeMap.get("SUP_CREATE")).link());
        processLinkList.add(ProcessElementBuilder.instance("SUP_CREATE->BUYER_APPROVE").source(processNodeMap.get("SUP_CREATE")).target(processNodeMap.get("BUYER_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER_APPROVE->BUYER-APPROVE-GATEWAY")
                .source(processNodeMap.get("BUYER_APPROVE")).target(processNodeMap.get("BUYER-APPROVE-GATEWAY")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-APPROVE-GATEWAY->SUP_UPDATE")
                .source(processNodeMap.get("BUYER-APPROVE-GATEWAY")).target(processNodeMap.get("SUP_UPDATE")).conditionExpression("#buyerApprove == false").link());
        processLinkList.add(ProcessElementBuilder.instance("SUP_UPDATE->BUYER_APPROVE")
                .source(processNodeMap.get("SUP_UPDATE")).target(processNodeMap.get("BUYER_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-APPROVE-GATEWAY->PARALLEL-GATEWAY")
                .source(processNodeMap.get("BUYER-APPROVE-GATEWAY")).target(processNodeMap.get("PARALLEL-GATEWAY")).conditionExpression("#buyerApprove == true").link());
        processLinkList.add(ProcessElementBuilder.instance("PARALLEL-GATEWAY->BUYER_MANAGEMENT_APPROVE")
                .source(processNodeMap.get("PARALLEL-GATEWAY")).target(processNodeMap.get("BUYER_MANAGEMENT_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER_MANAGEMENT_APPROVE->BUYER-MANAGEMENT-GATEWAY")
                .source(processNodeMap.get("BUYER_MANAGEMENT_APPROVE")).target(processNodeMap.get("BUYER-MANAGEMENT-GATEWAY")).link());
        processLinkList.add(ProcessElementBuilder.instance("PARALLEL-GATEWAY->BUYER_DIRECTOR_APPROVE")
                .source(processNodeMap.get("PARALLEL-GATEWAY")).target(processNodeMap.get("BUYER_DIRECTOR_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER_DIRECTOR_APPROVE->BUYER-DIRECTOR-GATEWAY")
                .source(processNodeMap.get("BUYER_DIRECTOR_APPROVE")).target(processNodeMap.get("BUYER-DIRECTOR-GATEWAY")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-MANAGEMENT-GATEWAY->BUYER_MANAGEMENT_BUYER_UPDATE")
                .source(processNodeMap.get("BUYER-MANAGEMENT-GATEWAY")).target(processNodeMap.get("BUYER_MANAGEMENT_BUYER_UPDATE")).conditionExpression("#buyerManagementApprove == false").link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER_MANAGEMENT_BUYER_UPDATE->BUYER_MANAGEMENT_APPROVE")
                .source(processNodeMap.get("BUYER_MANAGEMENT_BUYER_UPDATE")).target(processNodeMap.get("BUYER_MANAGEMENT_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-MANAGEMENT-GATEWAY->MASTER_APPROVE")
                .source(processNodeMap.get("BUYER-MANAGEMENT-GATEWAY")).target(processNodeMap.get("MASTER_APPROVE")).conditionExpression("#buyerManagementApprove == true").link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-DIRECTOR-GATEWAY->BUYER_DIRECTOR_BUYER_UPDATE")
                .source(processNodeMap.get("BUYER-DIRECTOR-GATEWAY")).target(processNodeMap.get("BUYER_DIRECTOR_BUYER_UPDATE")).conditionExpression("#buyerDirectorApprove == false").link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER-DIRECTOR-GATEWAY->MASTER_APPROVE")
                .source(processNodeMap.get("BUYER-DIRECTOR-GATEWAY")).target(processNodeMap.get("MASTER_APPROVE")).conditionExpression("#buyerDirectorApprove == true").link());
        processLinkList.add(ProcessElementBuilder.instance("MASTER-GATEWAY->MASTER_BUYER_UPDATE")
                .source(processNodeMap.get("MASTER-GATEWAY")).target(processNodeMap.get("MASTER_BUYER_UPDATE")).conditionExpression("#masterApprove == false").link());
        processLinkList.add(ProcessElementBuilder.instance("MASTER_APPROVE->MASTER-GATEWAY")
                .source(processNodeMap.get("MASTER_APPROVE")).target(processNodeMap.get("MASTER-GATEWAY")).link());
        processLinkList.add(ProcessElementBuilder.instance("MASTER-GATEWAY->END")
                .source(processNodeMap.get("MASTER-GATEWAY")).target(processNodeMap.get("END")).conditionExpression("#masterApprove == true").link());
        processLinkList.add(ProcessElementBuilder.instance("MASTER_BUYER_UPDATE->MASTER_APPROVE")
                .source(processNodeMap.get("MASTER_BUYER_UPDATE")).target(processNodeMap.get("MASTER_APPROVE")).link());
        processLinkList.add(ProcessElementBuilder.instance("BUYER_DIRECTOR_BUYER_UPDATE->BUYER_DIRECTOR_APPROVE")
                .source(processNodeMap.get("BUYER_DIRECTOR_BUYER_UPDATE")).target(processNodeMap.get("BUYER_DIRECTOR_APPROVE")).link());
        processLinkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(),
                Optional.ofNullable(link.getConditionExpression()).orElse("")));
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
        paraMap.put("buyerApprove", true);
        paraMap.put("buyerManagementApprove", true);
        paraMap.put("buyerDirectorApprove", true);
        paraMap.put("masterApprove", false);
        processNodeMap
                .forEach((key, value) -> {
                    ProcessNodeWrap<BaseProcessNode> processNodeWrap = baseDefaultProcessEngine.getNextProcessNode(key, paraMap);
                    ProcessNodeWrap<BaseProcessNode> expectNode = this.graphNext(baseDefaultProcessEngine.getProcessNode(key), paraMap);
                    Assert.assertEquals(expectNode, processNodeWrap);
                });
    }

    @Test
    public void getNextProcessNodeByNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("buyerApprove", true);
        paraMap.put("buyerManagementApprove", true);
        paraMap.put("buyerDirectorApprove", true);
        paraMap.put("masterApprove", false);
        processNodeMap.forEach((key, node) -> {
            ProcessNodeWrap<BaseProcessNode> processNodeWrap = baseDefaultProcessEngine.getNextProcessNode(node, paraMap);
            ProcessNodeWrap<BaseProcessNode> expectNode = this.graphNext(node, paraMap);
            Assert.assertEquals(expectNode, processNodeWrap);
        });
    }

    @Test
    public void forecastProcessNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("buyerApprove", true);
        paraMap.put("buyerManagementApprove", true);
        paraMap.put("buyerDirectorApprove", true);
        paraMap.put("masterApprove", false);
        processNodeMap.forEach((id, node) -> {
            Set<BaseProcessNode> processNodeList = baseDefaultProcessEngine.forecastProcessNode(node, paraMap);
            Set<BaseProcessNode> expectNodeList = new HashSet<>();
            graphForecast(node, paraMap, expectNodeList);
            Assert.assertEquals(expectNodeList, processNodeList);
        });
    }

    private ProcessNodeWrap<BaseProcessNode> graphNext(final BaseProcessNode currentNode, final Map<String, Object> paraMap) {
        boolean containsFlag = directedValueGraph.nodes().contains(currentNode);
        if (!containsFlag || currentNode instanceof EndNode) {
            return null;
        }
        Set<Edge<BaseProcessNode>> edges = directedValueGraph.outgoingEdges(currentNode);
        if (edges == null || edges.size() < 1) {
            return null;
        }
        if (currentNode instanceof ParallelGateway) {
            return parallelGatewayHandler(edges, paraMap);
        } else if (currentNode instanceof ExclusiveGateway) {
            return exclusiveGatewayHandler(edges, paraMap);
        } else {
            return normalNodeHandler(edges, paraMap);
        }
    }

    private void graphForecast(final BaseProcessNode currentNode,
                               final Map<String, Object> paraMap,
                               final Set<BaseProcessNode> forecastNodes) {
        if (forecastNodes.contains(currentNode)) {
            return;
        }
        forecastNodes.add(currentNode);
        if (currentNode instanceof EndNode) {
            return;
        }
        ProcessNodeWrap<BaseProcessNode> nextNodeWrap = graphNext(currentNode, paraMap);
        if (nextNodeWrap == null) {
            return;
        }
        if (!nextNodeWrap.parallel()) {
            graphForecast(nextNodeWrap.get(), paraMap, forecastNodes);
        } else {
            nextNodeWrap.getParallelNodes().forEach(node -> graphForecast(node, paraMap, forecastNodes));
        }
    }

    private ProcessNodeWrap<BaseProcessNode> parallelGatewayHandler(final Set<Edge<BaseProcessNode>> edges, final Map<String, Object> paraMap) {
        return ProcessNodeWrap.parallel(edges.stream()
                .filter(edge -> {
                    Optional<String> expressStrOpt = directedValueGraph.edgeValue(edge);
                    if (expressStrOpt.isPresent() && expressStrOpt.get().trim().length() > 0) {
                        return baseExecutor.executeExpression(expressStrOpt.get(), paraMap);
                    }
                    return true;
                }).map(Edge::target).collect(Collectors.toSet()));
    }

    private ProcessNodeWrap<BaseProcessNode> exclusiveGatewayHandler(final Set<Edge<BaseProcessNode>> edges, final Map<String, Object> paraMap) {
        Optional<Edge<BaseProcessNode>> edgeOptional = edges.stream()
                .filter(edge -> {
                    Optional<String> expressStrOpt = directedValueGraph.edgeValue(edge);
                    if (expressStrOpt.isPresent() && expressStrOpt.get().trim().length() > 0) {
                        return baseExecutor.executeExpression(expressStrOpt.get(), paraMap);
                    }
                    return false;
                }).findFirst();
        return edgeOptional.map(baseProcessNodeEdge -> ProcessNodeWrap.unParallel(baseProcessNodeEdge.target())).orElse(null);
    }

    private ProcessNodeWrap<BaseProcessNode> normalNodeHandler(final Set<Edge<BaseProcessNode>> edges, final Map<String, Object> paraMap) {
        Set<BaseProcessNode> nextNodes = edges.stream()
                .filter(edge -> {
                    Optional<String> expressStrOpt = directedValueGraph.edgeValue(edge);
                    if (expressStrOpt.isPresent() && expressStrOpt.get().trim().length() > 0) {
                        return baseExecutor.executeExpression(expressStrOpt.get(), paraMap);
                    }
                    return true;
                }).map(Edge::target).collect(Collectors.toSet());
        if (nextNodes.size() < 1) {
            return null;
        }
        List<BaseProcessNode> nextNodeList = new ArrayList<>(nextNodes);
        BaseProcessNode next = nextNodeList.get(0);
        boolean gatewayFlag = next instanceof BaseGateway;
        return gatewayFlag ? graphNext(next, paraMap) : ProcessNodeWrap.unParallel(next);
    }
}
