package com.bluslee.mundo.core.test.process;

import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseProcessEngine;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessNodeWrap;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.core.process.graph.Edge;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description BaseDefaultProcessEngineTest
 */
public class BaseDefaultProcessEngineImplTest<processNodeWrap> {

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
        add(ProcessElementBuilder.instance("start-node2supplier-create")
                .name("开始节点2供应商创建单据")
                .source(processNodeMap.get("start-node"))
                .target(processNodeMap.get("supplier-create"))
                .conditionExpression("")
                .link());
        add(ProcessElementBuilder.instance("supplier-create2supplier-submit")
                .name("供应商创建单据2供应商提交单据")
                .source(processNodeMap.get("supplier-create"))
                .target(processNodeMap.get("supplier-submit"))
                .conditionExpression("")
                .link());
        add(ProcessElementBuilder.instance("supplier-submit2buyer-approve")
                .name("供应商提交单据2采购审批")
                .source(processNodeMap.get("supplier-submit"))
                .target(processNodeMap.get("buyer-approve"))
                .conditionExpression("")
                .link());
        add(ProcessElementBuilder.instance("supplier-update2buyer-approve")
                .name("供应商修改2采购审批")
                .source(processNodeMap.get("supplier-update"))
                .target(processNodeMap.get("buyer-approve"))
                .conditionExpression("")
                .link());
        add(ProcessElementBuilder.instance("buyer-approve2buyer-approve-gateway")
                .name("采购审批2采购审批网关")
                .source(processNodeMap.get("buyer-approve"))
                .target(processNodeMap.get("buyer-approve-gateway"))
                .conditionExpression("")
                .link());
        add(ProcessElementBuilder.instance("buyer-approve-gateway2supplier-update")
                .name("采购审批网关2供应商修改")
                .source(processNodeMap.get("buyer-approve-gateway"))
                .target(processNodeMap.get("supplier-update"))
                .conditionExpression("# approved == false")
                .link());
        add(ProcessElementBuilder.instance("buyer-approve-gateway2approve-end")
                .name("采购审批网关2审批结束")
                .source(processNodeMap.get("buyer-approve-gateway"))
                .target(processNodeMap.get("approve-end"))
                .conditionExpression("# approved == true")
                .link());
    }};
    private final Map<String, BaseProcessNode> processNextNodeMap = new HashMap<String, BaseProcessNode>() {{
        put("start-node", processNodeMap.get("supplier-create"));
        put("supplier-create", processNodeMap.get("supplier-submit"));
        put("supplier-submit", processNodeMap.get("buyer-approve"));
        put("buyer-approve-true", processNodeMap.get("approve-end"));
        put("buyer-approve-false", processNodeMap.get("supplier-update"));
        put("supplier-update", processNodeMap.get("buyer-approve"));
        put("approve-end", processNodeMap.get("approve-end"));
    }};
    private final DirectedValueGraphImpl<BaseProcessNode, String> directedValueGraph = new DirectedValueGraphImpl<>();
    private final BaseExecutor baseExecutor = new BaseExecutor() {
    };

    @Before
    public void buildProcess() {
        processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
        processLinkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression()));
        baseDefaultProcessEngine = new BaseProcessEngine<BaseProcessNode, String>
                ("test-process", baseExecutor, directedValueGraph) {
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
                    ProcessNodeWrap<BaseProcessNode> expectNode = this.directGraphNext(value, paraMap);
                    Assert.assertEquals(expectNode, processNodeWrap);
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

    private ProcessNodeWrap<BaseProcessNode> directGraphNext(BaseProcessNode currentNode, Map<String, Object> paraMap) {
        boolean containsFlag = directedValueGraph.nodes().contains(currentNode);
        if (!containsFlag) {
            return null;
        }
        Set<Edge<BaseProcessNode>> edges = directedValueGraph.outgoingEdges(currentNode);
        if (edges != null && edges.size() > 0) {
            if (currentNode instanceof ParallelGateway) {
                //并行网关
                return ProcessNodeWrap.parallel(edges.stream()
                        .filter(edge -> {
                            Optional<String> expressStrOpt = directedValueGraph.edgeValue(edge);
                            if (expressStrOpt.isPresent() && expressStrOpt.get().trim().length() > 0) {
                                return baseExecutor.executeExpression(expressStrOpt.get(), paraMap);
                            }
                            return true;
                        }).map(Edge::target).collect(Collectors.toSet()));
            } else {
                Set<BaseProcessNode> nextSet = edges.stream()
                        .filter(edge -> {
                            Optional<String> expressStrOpt = directedValueGraph.edgeValue(edge);
                            if (expressStrOpt.isPresent() && expressStrOpt.get().trim().length() > 0) {
                                return baseExecutor.executeExpression(expressStrOpt.get(), paraMap);
                            }
                            return true;
                        }).map(Edge::target).collect(Collectors.toSet());
                List<BaseProcessNode> nextNodeList = new ArrayList<>(nextSet);
                return ProcessNodeWrap.unParallel(nextNodeList.get(0));
            }
        }
        return null;
    }

}
