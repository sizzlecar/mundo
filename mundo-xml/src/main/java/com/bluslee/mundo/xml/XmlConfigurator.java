package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryBuilder;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.Activity;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.ExclusiveGateway;
import com.bluslee.mundo.core.process.ParallelGateway;
import com.bluslee.mundo.core.process.StartNode;
import com.bluslee.mundo.core.process.Link;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.ProcessEngineImpl;
import com.bluslee.mundo.core.process.ProcessEngineBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.xml.base.XmlParser;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * XmlParserImpl.
 *
 * @author carl.che
 */
public abstract class XmlConfigurator<N extends BaseProcessNode> implements RepositoryBuilder<N> {

    private final XmlParser xmlParser;

    public XmlConfigurator(final XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    /**
     * 根据配置构建出流程图BaseProcess的示例.
     *
     * @return Repository
     */
    @Override
    public Repository<N> build(final Configuration configuration) {
        synchronized (XmlConfigurator.class) {
            configuration.init();
            validatorPipLine.validate(configuration, (configuration, validatorPipLine, model) -> validatorPipLine.getValidators()
                    .forEach(validator -> validator.validate(configuration, model)), null);
            XmlSchema xmlSchema = parseXml();
            validatorPipLine.validate(configuration, (configuration, validatorPipLine, model) -> validatorPipLine.getValidators()
                    .forEach(validator -> validator.validate(configuration, model)), xmlSchema);
            return processSchema2Repository(xmlSchema.getProcessList());
        }
    }

    private XmlSchema parseXml() throws MundoException {
        return (XmlSchema) xmlParser.fromXML(new ByteArrayInputStream(configuration.getInitData()));
    }

    private Repository<N> processSchema2Repository(final List<XmlSchema.ProcessSchema> processList) {
        Set<ProcessEngine<BaseProcessNode>> processEngineList = processList
                .stream()
                .map(process -> {
                    List<XmlSchema.ProcessExclusiveGatewaySchema> exclusiveGatewayList = Optional.ofNullable(process.getExclusiveGatewayList())
                            .orElse(Collections.emptyList());
                    List<XmlSchema.ProcessParallelGatewaySchema> parallelGatewayList = Optional.ofNullable(process.getParallelGatewayList())
                            .orElse(Collections.emptyList());
                    List<XmlSchema.ProcessLinkSchema> processLinkSchemaList = process.getLinkList();
                    List<XmlSchema.ProcessNodeSchema> processNodeList = process.getActivityList();
                    List<XmlSchema.ProcessEndNodeSchema> endList = process.getEndList();
                    List<XmlSchema.ProcessStartNodeSchema> startList = process.getStartList();
                    List<ExclusiveGateway> exclusiveGateways = exclusiveGatewayList.stream()
                            .map(gateway -> ProcessElementBuilder.instance(gateway.getId()).name(gateway.getName()).exclusiveGateway())
                            .collect(Collectors.toList());
                    List<ParallelGateway> parallelGateways = parallelGatewayList.stream()
                            .map(gateway -> ProcessElementBuilder.instance(gateway.getId()).name(gateway.getName()).parallelGateway())
                            .collect(Collectors.toList());
                    List<Activity> activityList = processNodeList.stream()
                            .map(node -> ProcessElementBuilder.instance(node.getId()).name(node.getName()).activity())
                            .collect(Collectors.toList());
                    List<EndNode> endNodeList = endList.stream()
                            .map(end -> ProcessElementBuilder.instance(end.getId()).name(end.getName()).endNode())
                            .collect(Collectors.toList());
                    List<StartNode> startNodeList = startList.stream()
                            .map(start -> ProcessElementBuilder.instance(start.getId()).name(start.getName()).startNode())
                            .collect(Collectors.toList());
                    List<BaseProcessNode> baseProcessNodes = new ArrayList<>();
                    baseProcessNodes.addAll(startNodeList);
                    baseProcessNodes.addAll(activityList);
                    baseProcessNodes.addAll(exclusiveGateways);
                    baseProcessNodes.addAll(parallelGateways);
                    baseProcessNodes.addAll(endNodeList);
                    Map<String, N> processNodeMap = baseProcessNodes.stream().collect(Collectors.toMap(BaseProcessNode::getId, item -> (N) item));
                    List<Link> linkList = processLinkSchemaList.stream().map(link -> ProcessElementBuilder
                            .instance(link.getId())
                            .name(link.getName())
                            .source(processNodeMap.get(link.getSourceId()))
                            .target(processNodeMap.get(link.getTargetId()))
                            .conditionExpression(link.getConditionExpression())
                            .link())
                            .collect(Collectors.toList());
                    DirectedValueGraphImpl<BaseProcessNode, Object> directedValueGraph = new DirectedValueGraphImpl<>();
                    processNodeMap.forEach((id, node) -> directedValueGraph.addNode(node));
                    linkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(),
                            link.getConditionExpression() == null ? "" : link.getConditionExpression()));
                    ProcessEngineImpl<BaseProcessNode, Object> processEngineImpl = ProcessEngineBuilder
                            .instance(process.getId())
                            .version(process.getVersion())
                            .execute(new BaseExecutor() {
                            }).directedValueGraph(directedValueGraph).build();
                    return (ProcessEngine<BaseProcessNode>) processEngineImpl;
                }).collect(Collectors.toSet());
        return (Repository<N>) com.bluslee.mundo.core.process.RepositoryBuilder.build(processEngineList);
    }

}
