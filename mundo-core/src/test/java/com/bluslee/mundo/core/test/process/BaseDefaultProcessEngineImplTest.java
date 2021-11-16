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
            add(ProcessElementBuilder.instance("buyer-approve2buyer-approve-gateway").name("采购审批2采购审批网关")
                .source(processNodeMap.get("buyer-approve")).target(processNodeMap.get("buyer-approve-gateway"))
                .conditionExpression("").link());
            add(ProcessElementBuilder.instance("buyer-approve-gateway2supplier-update").name("采购审批网关2供应商修改")
                .source(processNodeMap.get("buyer-approve-gateway")).target(processNodeMap.get("supplier-update"))
                .conditionExpression("# approved == false").link());
            add(ProcessElementBuilder.instance("buyer-approve-gateway2approve-end").name("采购审批网关2审批结束")
                .source(processNodeMap.get("buyer-approve-gateway")).target(processNodeMap.get("approve-end"))
                .conditionExpression("# approved == true").link());
        }};

    private final DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();

    private final BaseExecutor baseExecutor = new BaseExecutor() {
    };

    @Before
    public void buildProcess() {
        processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
        processLinkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression()));
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
        paraMap.put("approved", true);
        processNodeMap.forEach((key, node) -> {
            ProcessNodeWrap<BaseProcessNode> processNodeWrap = baseDefaultProcessEngine.getNextProcessNode(node, paraMap);
            ProcessNodeWrap<BaseProcessNode> expectNode = this.graphNext(node, paraMap);
            Assert.assertEquals(expectNode, processNodeWrap);
        });
    }

    @Test
    public void forecastProcessNodeTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("approved", false);
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
