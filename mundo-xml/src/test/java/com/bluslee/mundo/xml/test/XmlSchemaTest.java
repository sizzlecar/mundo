package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
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

}
