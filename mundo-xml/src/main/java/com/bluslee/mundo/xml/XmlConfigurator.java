package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.BaseExecutor;
import com.bluslee.mundo.core.process.Activity;
import com.bluslee.mundo.core.process.EndNode;
import com.bluslee.mundo.core.process.ExclusiveGateway;
import com.bluslee.mundo.core.process.StartNode;
import com.bluslee.mundo.core.process.Link;
import com.bluslee.mundo.core.process.ProcessElementBuilder;
import com.bluslee.mundo.core.process.ProcessEngineImpl;
import com.bluslee.mundo.core.process.ProcessEngineBuilder;
import com.bluslee.mundo.core.process.RepositoryBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.process.graph.DirectedValueGraphImpl;
import com.bluslee.mundo.xml.base.XmlParser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * XmlParserImpl.
 * @author carl.che
 */
public abstract class XmlConfigurator<N extends BaseProcessNode> implements Configurator<N> {

    private static final String XML_PATH_CONFIG_NAME = "mundo.xml-path";

    private final XmlParser xmlParser;

    private final Properties properties = new Properties();

    private final Validator validation = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * xml字符串.
     */
    private String xmlStr;

    /**
     * xml文件inputStream.
     */
    private InputStream inputStream;

    /**
     * xml文件file.
     */
    private File file;

    public XmlConfigurator(final XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    /**
     * 配置 xmlStr.
     * @param xmlStr xmlStr
     * @return this
     */
    public XmlConfigurator<N> xmlStr(final String xmlStr) {
        this.xmlStr = xmlStr;
        return this;
    }

    /**
     * 配置 inputStream.
     * @param inputStream inputStream
     * @return this
     */
    public XmlConfigurator<N> inputStream(final InputStream inputStream) {
        this.inputStream = inputStream;
        return this;
    }

    /**
     * 配置 file.
     * @param file file
     * @return this
     */
    public XmlConfigurator<N> file(final File file) {
        this.file = file;
        return this;
    }

    /**
     * setProperty.
     * @param key   属性名
     * @param value 值
     */
    @Override
    public void setProperty(final String key, final String value) {
        this.properties.setProperty(key, value);
    }

    /**
     * getProperty.
     * @param key 属性key
     * @return Object
     */
    @Override
    public Object getProperty(final String key) {
        return this.properties.getProperty(key);
    }

    /**
     * load inStream.
     * @param inStream 配置文件InputStream
     */
    @Override
    public void load(final InputStream inStream) {
        try {
            properties.load(inStream);
        } catch (IOException e) {
            throw new MundoException("读取配置文件发生异常", e);
        }
    }

    /**
     * 根据配置构建出流程图BaseProcess的示例.
     * @return Repository
     */
    @Override
    public Repository<N> build() {
        synchronized (XmlConfigurator.class) {
            XmlSchema xmlSchema = parseXml();
            validate(xmlSchema);
            List<XmlSchema.ProcessSchema> processList = xmlSchema.getProcessList();
            return processSchema2Repository(processList);
        }
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

    private void validate(final XmlSchema xmlSchema) throws MundoException {
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

    private Repository<N> processSchema2Repository(final List<XmlSchema.ProcessSchema> processList) {
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
                    .instance(process.getId())
                    .version(process.getVersion())
                    .execute(new BaseExecutor() {
                        @Override
                        public <T, E> T executeExpression(final E expression, final Map<String, Object> parameterMap) {
                            return super.executeExpression(expression, parameterMap);
                        }
                    }).directedValueGraph(directedValueGraph).build();
            return (ProcessEngine<BaseProcessNode>) processEngineImpl;
        }).collect(Collectors.toSet());
        return (Repository<N>) RepositoryBuilder.build(processEngineImpls);
    }

}
