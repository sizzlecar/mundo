package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.*;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.xml.base.XmlParser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description XmlParserImpl
 */
public abstract class XmlConfigurator<N extends BaseProcessNode> implements Configurator<N> {

    private final XmlParser xmlParser;
    private final Properties properties = new Properties();
    private final Validator validation = Validation.buildDefaultValidatorFactory().getValidator();
    private final static String XML_PATH_CONFIG_NAME = "mundo.xml-path";

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

    public XmlConfigurator<N> xmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
        return this;
    }

    public XmlConfigurator<N> inputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    public XmlConfigurator<N> file(File file) {
        this.file = file;
        return this;
    }

    @Override
    public void setProperty(String key, String value) {
        this.properties.setProperty(key, value);
    }

    @Override
    public Object getProperty(String key) {
        return this.properties.getProperty(key);
    }

    @Override
    public void load(InputStream inStream) {
        try {
            properties.load(inStream);
        } catch (IOException e) {
            throw new MundoException("读取配置文件发生异常", e);
        }
    }

    @Override
    public Repository<N> build() {
        XmlSchema xmlSchema = parseXml();
        validate(xmlSchema);
        List<XmlSchema.ProcessSchema> processList = xmlSchema.getProcessList();
        return processSchema2Repository(processList);
    }

    private XmlSchema parseXml() throws MundoException {
        XmlSchema xmlSchema = null;
        String property = properties.getProperty(XML_PATH_CONFIG_NAME);
        if (this.inputStream != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(inputStream);
        } else if (this.file != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(file);
        } else if (this.xmlStr != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(xmlStr);
        } else if (property != null) {
            xmlSchema = (XmlSchema) xmlParser.fromXML(getClass().getResourceAsStream(property));
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

    private Repository<N> processSchema2Repository(List<XmlSchema.ProcessSchema> processList) {
        Set<ProcessEngine<BaseProcessNode>> processEngineImpls = processList.stream().map(process -> {
            List<XmlSchema.ProcessExclusiveGatewaySchema> exclusiveGatewayList = process.getExclusiveGatewayList();
            List<XmlSchema.ProcessLinkSchema> processLinkSchemaList = process.getLinkList();
            List<XmlSchema.ProcessNodeSchema> processNodeList = process.getActivityList();
            List<XmlSchema.ProcessEndNodeSchema> endList = process.getEndList();
            List<XmlSchema.ProcessStartNodeSchema> startList = process.getStartList();
            List<ExclusiveGateway> exclusiveGateways = exclusiveGatewayList.stream()
                    .map(gateway -> ProcessElementBuilder.instance(gateway.getId()).name(gateway.getName()).exclusiveGateway())
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
            baseProcessNodes.addAll(exclusiveGateways);
            baseProcessNodes.addAll(activityList);
            baseProcessNodes.addAll(endNodeList);
            baseProcessNodes.addAll(startNodeList);
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
            linkList.forEach(link -> directedValueGraph.putEdgeValue(link.getSource(), link.getTarget(), link.getConditionExpression() == null ? "" : link.getConditionExpression()));
            ProcessEngineImpl<BaseProcessNode, Object> processEngineImpl = ProcessEngineBuilder
                    .instance(process.getId()).execute(new BaseExecutor() {
                        @Override
                        public <T, E> T executeExpression(E expression, Map<String, Object> parameterMap) {
                            return super.executeExpression(expression, parameterMap);
                        }
                    }).directedValueGraph(directedValueGraph).build();
            return (ProcessEngine<BaseProcessNode>) processEngineImpl;
        }).collect(Collectors.toSet());
        return (Repository<N>) RepositoryBuilder.build(processEngineImpls);
    }


}
