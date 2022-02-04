package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * XmlSchemaTest.
 * @author carl.che
 */
public class XmlSchemaTest extends XmlProcessor {
    private static final String FILE_PATH = "/mundo.cfg.xml";

    public XmlSchemaTest() {
        super(FILE_PATH);
    }

    @Test
    public void xml2BeanTest() {
        List<XmlSchema.ProcessSchema> processSchemas = getProcessSchemas();
        InputStream mundoXmlStream = XmlSchemaTest.class.getResourceAsStream("/mundo.cfg.xml");
        XStream xStream = new XStream();
        xStream.processAnnotations(XmlSchema.class);
        xStream.allowTypesByWildcard(new String[]{"com.bluslee.mundo.**"});
        XmlSchema xmlSchema = (XmlSchema) xStream.fromXML(mundoXmlStream);
        Assert.assertEquals(processSchemas.size(), xmlSchema.getProcessList().size());
        for (int i = 0; i < xmlSchema.getProcessList().size(); i++) {
            XmlSchema.ProcessSchema actual = xmlSchema.getProcessList().get(i);
            XmlSchema.ProcessSchema expect = processSchemas.get(i);
            Assert.assertEquals(expect.getName(), actual.getName());
            Assert.assertEquals(expect.getVersion(), actual.getVersion());
            Assert.assertEquals(expect.getActivityList(), actual.getActivityList());
            Assert.assertEquals(expect.getExclusiveGatewayList(), actual.getExclusiveGatewayList());
            Assert.assertEquals(expect.getEndList(), actual.getEndList());
            Assert.assertEquals(expect.getStartList(), actual.getStartList());
            Assert.assertEquals(expect.getLinkList(), actual.getLinkList());
        }
    }

    @Test
    public void equalsTest() {
        XmlSchema xmlSchema = new XmlSchema();
        XmlSchema.ProcessSchema processSchema1 = new XmlSchema.ProcessSchema();
        processSchema1.setId("processSchema1");
        XmlSchema.ProcessSchema processSchema2 = new XmlSchema.ProcessSchema();
        processSchema2.setId("processSchema2");
        xmlSchema.setProcessList(Collections.singletonList(processSchema1));
        Assert.assertEquals(xmlSchema, xmlSchema);
        XmlSchema xmlSchema1 = new XmlSchema();
        xmlSchema1.setProcessList(Collections.singletonList(processSchema1));
        Assert.assertEquals(xmlSchema, xmlSchema1);
        xmlSchema1.setProcessList(null);
        Assert.assertNotEquals(xmlSchema, xmlSchema1);
        Object obj = new Object();
        Assert.assertNotEquals(xmlSchema, obj);
        Assert.assertNotEquals(xmlSchema, null);
    }

    @Test
    public void hashcodeTest() {
        XmlSchema xmlSchema = new XmlSchema();
        XmlSchema xmlSchema1 = new XmlSchema();
        XmlSchema.ProcessSchema processSchema = new XmlSchema.ProcessSchema();
        processSchema.setId("processSchema");
        xmlSchema.setProcessList(Collections.singletonList(processSchema));
        Assert.assertNotEquals(xmlSchema.hashCode(), xmlSchema1.hashCode());
    }
}
