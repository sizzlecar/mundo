package com.bluslee.mundo.xml.test;

import org.junit.Test;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

/**
 * 使用XSD校验XML是否正确单元测试.
 *
 * @author carl.che
 * @date 2021/11/22
 */
public class XmlValidateByXsdTest {

    private final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    @Test
    public void validateXmlTest() throws Exception {
        String xsdFilePath = "/mundo.xsd";
        String xmlFilePath = "/mundo.cfg.xml";
        Schema schema = schemaFactory.newSchema(new StreamSource(getClass().getResourceAsStream(xsdFilePath)));
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(getClass().getResourceAsStream(xmlFilePath)));
    }

}
