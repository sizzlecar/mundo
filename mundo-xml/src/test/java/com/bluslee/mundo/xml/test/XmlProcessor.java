package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.xml.XmlSchema;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.SAXReader;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * 提供对XML解析功能.
 * @author carl.che
 */
public abstract class XmlProcessor {

    private final Document document;

    private final String defaultNs = "mundo";

    private final String xpathDefaultNs = "mundo:";

    public XmlProcessor(final String path) {
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(XmlProcessor.class.getResourceAsStream(path));
            Element rootElement = document.getRootElement();
            String namespaceURI = rootElement.getNamespaceURI();
            List<Namespace> namespaces = rootElement.additionalNamespaces();
            Map<String, String> nsMap = new HashMap<>();
            nsMap.put(defaultNs, namespaceURI);
            namespaces.forEach(ns -> nsMap.put(ns.getPrefix(), ns.getURI()));
            reader.getDocumentFactory().setXPathNamespaceURIs(nsMap);
        } catch (DocumentException e) {
            throw new MundoException("解析XML发生错误");
        }
    }

    /**
     * 根据xPath查询XML node.
     * @param xPathExpression xPath 表达式
     * @return 查询到的节点
     */
    protected List<Node> selectNodeByXpath(final String xPathExpression) {
        return document.selectNodes(xPathExpression);
    }

    /**
     * 获取XML文件对应的XmlSchema model.
     * @return XmlSchema model
     */
    protected XmlSchema getXmlSchema() {
        XmlSchema xmlSchema = new XmlSchema();
        xmlSchema.setProcessList(getProcessSchemas());
        return xmlSchema;
    }

    protected List<XmlSchema.ProcessSchema> getProcessSchemas() {
        List<Node> processNodes = document.selectNodes("/" + xpathDefaultNs + "mundo/" + xpathDefaultNs + "process");
        return processNodes.stream().map(processNode -> {
            XmlSchema.ProcessSchema processSchema = new XmlSchema.ProcessSchema();
            processSchema.setId(processNode.valueOf("@id"));
            processSchema.setName(processNode.valueOf("@name"));
            Optional.ofNullable(processNode.valueOf("@version"))
                    .ifPresent(s -> processSchema.setVersion(Integer.parseInt(s)));
            processSchema.setStartList(getProcessStartNodeSchemas(processNode));
            processSchema.setActivityList(getProcessNodeSchemas(processNode));
            processSchema.setExclusiveGatewayList(getProcessExclusiveGatewaySchemas(processNode));
            processSchema.setParallelGatewayList(getProcessParallelGatewaySchemas(processNode));
            processSchema.setLinkList(getProcessLinkSchemas(processNode));
            processSchema.setEndList(getProcessEndNodeSchema(processNode));
            return processSchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessStartNodeSchema> getProcessStartNodeSchemas(final Node processNode) {
        List<Node> startNodes = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "start"))
                .orElse(Collections.emptyList());
        return startNodes.stream().map(startNode -> {
            XmlSchema.ProcessStartNodeSchema processStartNodeSchema = new XmlSchema.ProcessStartNodeSchema();
            processStartNodeSchema.setId(startNode.valueOf("@id"));
            processStartNodeSchema.setName(startNode.valueOf("@name"));
            return processStartNodeSchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessNodeSchema> getProcessNodeSchemas(final Node processNode) {
        List<Node> activities = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "activity"))
                .orElse(Collections.emptyList());
        return activities.stream().map(startNode -> {
            XmlSchema.ProcessNodeSchema processNodeSchema = new XmlSchema.ProcessNodeSchema();
            processNodeSchema.setId(startNode.valueOf("@id"));
            processNodeSchema.setName(startNode.valueOf("@name"));
            return processNodeSchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessExclusiveGatewaySchema> getProcessExclusiveGatewaySchemas(final Node processNode) {
        List<Node> exclusiveGateways = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "exclusiveGateway"))
                .orElse(Collections.emptyList());
        return exclusiveGateways.stream().map(exclusiveNode -> {
            XmlSchema.ProcessExclusiveGatewaySchema exclusiveGatewaySchema = new XmlSchema.ProcessExclusiveGatewaySchema();
            exclusiveGatewaySchema.setId(exclusiveNode.valueOf("@id"));
            exclusiveGatewaySchema.setName(exclusiveNode.valueOf("@name"));
            return exclusiveGatewaySchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessParallelGatewaySchema> getProcessParallelGatewaySchemas(final Node processNode) {
        List<Node> parallelGateways = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "parallelGateway"))
                .orElse(Collections.emptyList());
        return parallelGateways.stream().map(parallelNode -> {
            XmlSchema.ProcessParallelGatewaySchema parallelGatewaySchema = new XmlSchema.ProcessParallelGatewaySchema();
            parallelGatewaySchema.setId(parallelNode.valueOf("@id"));
            parallelGatewaySchema.setName(parallelNode.valueOf("@name"));
            return parallelGatewaySchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessLinkSchema> getProcessLinkSchemas(final Node processNode) {
        List<Node> links = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "link"))
                .orElse(Collections.emptyList());
        return links.stream().map(linkNode -> {
            XmlSchema.ProcessLinkSchema processLinkSchema = new XmlSchema.ProcessLinkSchema();
            processLinkSchema.setId(linkNode.valueOf("@id"));
            processLinkSchema.setName(linkNode.valueOf("@name"));
            processLinkSchema.setSourceId(linkNode.valueOf("@sourceId"));
            processLinkSchema.setTargetId(linkNode.valueOf("@targetId"));
            Optional.ofNullable(linkNode.selectSingleNode(xpathDefaultNs + "conditionExpression"))
                    .ifPresent(node -> processLinkSchema.setConditionExpression(node.getText()));
            return processLinkSchema;
        }).collect(Collectors.toList());
    }

    protected List<XmlSchema.ProcessEndNodeSchema> getProcessEndNodeSchema(final Node processNode) {
        List<Node> links = Optional
                .ofNullable(processNode.selectNodes(xpathDefaultNs + "end"))
                .orElse(Collections.emptyList());
        return links.stream().map(endNode -> {
            XmlSchema.ProcessEndNodeSchema endNodeSchema = new XmlSchema.ProcessEndNodeSchema();
            endNodeSchema.setId(endNode.valueOf("@id"));
            endNodeSchema.setName(endNode.valueOf("@name"));
            return endNodeSchema;
        }).collect(Collectors.toList());
    }
}
