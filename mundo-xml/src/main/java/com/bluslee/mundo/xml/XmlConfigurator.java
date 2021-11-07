package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.xml.base.XmlParser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description XmlParserImpl
 */
public abstract class XmlConfigurator implements XmlParser, Configurator {

    private final XmlParser xmlParser;
    private final Validator validation = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * xml字符串
     */
    private String xmlStr;

    /**
     * xml文件inputStream
     */
    private InputStream inputStream;

    /**
     * xml文件file
     */
    private File file;

    public XmlConfigurator(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    public XmlConfigurator xmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
        return this;
    }

    public XmlConfigurator inputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public XmlConfigurator file(File file) {
        this.file = file;
        return this;
    }

    @Override
    public <N extends BaseProcessNode> Repository<N> build() {
        XmlSchema xmlSchema = parseXml();
        validate(xmlSchema);
        List<XmlSchema.ProcessSchema> processList = xmlSchema.getProcessList();
        Set<ProcessEngine<BaseProcessNode, String>> processEngines = processList.stream().map(process -> {
            List<XmlSchema.ProcessExclusiveGatewaySchema> exclusiveGatewayList = process.getExclusiveGatewayList();
            List<XmlSchema.ProcessLinkSchema> processLinkSchemaList = process.getLinkList();
            List<XmlSchema.ProcessNodeSchema> processNodeList = process.getActivityList();
            List<XmlSchema.ProcessEndNodeSchema> endList = process.getEndList();
            List<XmlSchema.ProcessStartNodeSchema> startList = process.getStartList();
            List<ExclusiveGateway> exclusiveGateways = exclusiveGatewayList.stream()
                    .map(gateway -> new ExclusiveGateway(gateway.getId(), gateway.getName()))
                    .collect(Collectors.toList());
            List<Activity> activityList = processNodeList.stream()
                    .map(node -> new Activity(node.getId(), node.getName()))
                    .collect(Collectors.toList());
            List<EndNode> endNodeList = endList.stream()
                    .map(end -> new EndNode(end.getId(), end.getName()))
                    .collect(Collectors.toList());
            List<StartNode> startNodeList = startList.stream()
                    .map(end -> new StartNode(end.getId(), end.getName()))
                    .collect(Collectors.toList());
            List<BaseProcessNode> baseProcessNodes = new ArrayList<>();
            baseProcessNodes.addAll(exclusiveGateways);
            baseProcessNodes.addAll(activityList);
            baseProcessNodes.addAll(endNodeList);
            baseProcessNodes.addAll(startNodeList);
            Map<String, BaseProcessNode> processNodeMap = baseProcessNodes.stream().collect(Collectors.toMap(BaseProcessNode::getId, item -> item));
            List<Link> linkList = processLinkSchemaList.stream().map(link -> new Link(link.getId(), link.getName(), processNodeMap.get(link.getSourceId()), processNodeMap.get(link.getTargetId()), link.getConditionExpression())).collect(Collectors.toList());
            DirectedValueGraphImpl directedValueGraph = new DirectedValueGraphImpl();
            processNodeMap.forEach((id, node) -> {
                directedValueGraph.addNode(node);
            });
            linkList.forEach(link -> {
                directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression());
            });
            ProcessEngine<BaseProcessNode, String> processEngine = ProcessEngineBuilder.instance(process.getId()).directedValueGraph(directedValueGraph).build();
            return processEngine;
        }).collect(Collectors.toSet());
        return (Repository<N>) RepositoryBuilder.build(processEngines);
    }

    private XmlSchema parseXml() throws MundoException {
        XmlSchema xmlSchema = null;
        if (this.inputStream != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(inputStream);
        } else if (this.file != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(file);
        } else if (this.xmlStr != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(xmlStr);
        } else {
            throw new MundoException("缺少xml文件配置");
        }
        return xmlSchema;
    }

    private void validate(XmlSchema xmlSchema) throws MundoException {
        Set<ConstraintViolation<XmlSchema>> violationSet = validation.validate(xmlSchema);
        if (violationSet != null && violationSet.size() > 0) {
            String errorMsg = "";
            for (ConstraintViolation<XmlSchema> constraintViolation : violationSet) {
                String template = constraintViolation.getMessageTemplate();
                if (template != null) {
                    errorMsg = template;
                    break;
                }
            }
            throw new MundoException(errorMsg);
        }
    }


}
