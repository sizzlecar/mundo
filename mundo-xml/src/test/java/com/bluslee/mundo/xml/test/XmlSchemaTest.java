package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * XmlSchemaTest.
 * @author carl.che
 */
public class XmlSchemaTest {

    @Test
    public void xml2BeanTest() {
        InputStream mundoXmlStream = XmlSchemaTest.class.getResourceAsStream("/mundo.cfg.xml");
        XStream xStream = new XStream();
        xStream.processAnnotations(XmlSchema.class);
        xStream.allowTypesByWildcard(new String[]{"com.bluslee.mundo.**"});
        XmlSchema xmlSchema = (XmlSchema) xStream.fromXML(mundoXmlStream);
        XmlSchema expectXmlSchema = expectXmlSchema();
        Assert.assertEquals(expectXmlSchema.getProcessList().size(), xmlSchema.getProcessList().size());
        for (int i = 0; i < xmlSchema.getProcessList().size(); i++) {
            XmlSchema.ProcessSchema actual = xmlSchema.getProcessList().get(i);
            XmlSchema.ProcessSchema expect = expectXmlSchema.getProcessList().get(i);
            Assert.assertEquals(expect.getName(), actual.getName());
            Assert.assertEquals(expect.getVersion(), actual.getVersion());
            Assert.assertEquals(expect.getActivityList(), actual.getActivityList());
            Assert.assertEquals(expect.getExclusiveGatewayList(), actual.getExclusiveGatewayList());
            Assert.assertEquals(expect.getEndList(), actual.getEndList());
            Assert.assertEquals(expect.getStartList(), actual.getStartList());
            Assert.assertEquals(expect.getLinkList(), actual.getLinkList());
        }
    }

    public XmlSchema expectXmlSchema() {
        final XmlSchema xmlSchema = new XmlSchema();
        XmlSchema.ProcessSchema processSchema = new XmlSchema.ProcessSchema();
        processSchema.setId("process-001");
        processSchema.setVersion(0);
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
        XmlSchema.ProcessLinkSchema buyerApprove2Gateway = new XmlSchema.ProcessLinkSchema("BUYER_APPROVE_buyer-approve-gateway", "BUYER_APPROVE_buyer-approve-gateway",
                buyerApprove.getId(), processExclusiveGatewaySchema.getId());
        XmlSchema.ProcessLinkSchema gateway2supUpdate =
                new XmlSchema.ProcessLinkSchema("buyer-approve-gateway_SUP_UPDATE", "buyer-approve-gateway_SUP_UPDATE", processExclusiveGatewaySchema.getId(), supUpdate.getId(), "#approve == false");
        XmlSchema.ProcessLinkSchema supUpdate2buyerApprove =
                new XmlSchema.ProcessLinkSchema("SUP_UPDATE_BUYER_APPROVE", "SUP_UPDATE_BUYER_APPROVE", supUpdate.getId(), buyerApprove.getId(), null);

        XmlSchema.ProcessLinkSchema gateway2end =
                new XmlSchema.ProcessLinkSchema("buyer-approve-gateway_end", "buyer-approve-gateway_end", processExclusiveGatewaySchema.getId(), end.getId(), "#approve == true");
        processSchema.setLinkList(Arrays.asList(start2supCreate, supCreate2submit, supSubmit2buyerApprove, buyerApprove2Gateway, gateway2supUpdate, supUpdate2buyerApprove, gateway2end));
        XmlSchema.ProcessSchema processSchema1 = (XmlSchema.ProcessSchema) processSchema.clone();
        processSchema1.setVersion(1);
        XmlSchema.ProcessSchema processSchema2 = (XmlSchema.ProcessSchema) processSchema.clone();
        processSchema2.setId("process-002");
        processSchema2.setName("简单流程2");
        xmlSchema.setProcessList(Arrays.asList(processSchema, processSchema1, processSchema2));
        return xmlSchema;
    }

}
