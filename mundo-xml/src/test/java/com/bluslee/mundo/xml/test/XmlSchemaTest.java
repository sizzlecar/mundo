package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author carl.che
 * @date 2021/10/30
 * @description XmlSchemaTest
 */
public class XmlSchemaTest {


    @Test
    public void bean2XmlTest(){
        XmlSchema xmlSchema = new XmlSchema();
        XmlSchema.ProcessSchema processSchema = new XmlSchema.ProcessSchema();
        processSchema.setId("process-001");
        processSchema.setName("简单流程");
        XmlSchema.ProcessStartNodeSchema start = new XmlSchema.ProcessStartNodeSchema("START", "开始");
        XmlSchema.ProcessNodeSchema supCreate = new XmlSchema.ProcessNodeSchema("SUP_CREATE", "供应商创建");
        XmlSchema.ProcessNodeSchema supSubmit = new XmlSchema.ProcessNodeSchema("SUP_SUBMIT", "供应商提交");
        XmlSchema.ProcessNodeSchema buyerApprove = new XmlSchema.ProcessNodeSchema("BUYER_APPROVE", "采购审批");
        XmlSchema.ProcessNodeSchema supUpdate = new XmlSchema.ProcessNodeSchema("SUP_UPDATE", "供应商修改");
        XmlSchema.ProcessEndNodeSchema end = new XmlSchema.ProcessEndNodeSchema("end", "审批结束");
        processSchema.setActivityList(Arrays.asList(supCreate, supSubmit, buyerApprove, supUpdate));
        processSchema.setStartList(Collections.singletonList(start));
        processSchema.setEndList(Collections.singletonList(end));
        XmlSchema.ProcessExclusiveGatewaySchema processExclusiveGatewaySchema = new XmlSchema.ProcessExclusiveGatewaySchema("buyer-approve-gateway", "采购审批");
        processSchema.setExclusiveGatewayList(Collections.singletonList(processExclusiveGatewaySchema));
        XmlSchema.ProcessLinkSchema start2supCreate = new XmlSchema.ProcessLinkSchema("START_SUP_CREATE", "START_SUP_CREATE", start.getId(), supCreate.getId());
        XmlSchema.ProcessLinkSchema supCreate2submit = new XmlSchema.ProcessLinkSchema("SUP_CREATE_SUP_SUBMIT", "SUP_CREATE_SUP_SUBMIT", supCreate.getId(), supSubmit.getId());
        XmlSchema.ProcessLinkSchema supSubmit2buyerApprove = new XmlSchema.ProcessLinkSchema("SUP_SUBMIT_BUYER_APPROVE", "SUP_SUBMIT_BUYER_APPROVE", supSubmit.getId(), buyerApprove.getId());
        XmlSchema.ProcessLinkSchema buyerApprove2Gateway = new XmlSchema.ProcessLinkSchema("BUYER_APPROVE_buyer-approve-gateway", "BUYER_APPROVE_buyer-approve-gateway", buyerApprove.getId(), processExclusiveGatewaySchema.getId());
        XmlSchema.ProcessLinkSchema gateway2supUpdate =
                new XmlSchema.ProcessLinkSchema("buyer-approve-gateway_SUP_UPDATE", "buyer-approve-gateway_SUP_UPDATE", processExclusiveGatewaySchema.getId(), supUpdate.getId(), "approve == false");
        XmlSchema.ProcessLinkSchema gateway2end =
                new XmlSchema.ProcessLinkSchema("buyer-approve-gateway_end", "buyer-approve-gateway_end", processExclusiveGatewaySchema.getId(), end.getId(), "approve == true");
        processSchema.setLinkList(Arrays.asList(supCreate2submit, supSubmit2buyerApprove, buyerApprove2Gateway, gateway2supUpdate, gateway2end));
        xmlSchema.setProcessList(Collections.singletonList(processSchema));
        XStream xStream = new XStream();
        xStream.processAnnotations(XmlSchema.class);
        System.out.println(xStream.toXML(xmlSchema));
    }

    @Test
    public void xml2BeanTest(){
        InputStream mundoXmlStream = XmlSchemaTest.class.getResourceAsStream("/mundo.cfg.xml");
        XStream xStream = new XStream();
        xStream.processAnnotations(XmlSchema.class);
        XmlSchema xmlSchema = (XmlSchema)xStream.fromXML(mundoXmlStream);
        System.out.println(xmlSchema.toString());
    }

}
