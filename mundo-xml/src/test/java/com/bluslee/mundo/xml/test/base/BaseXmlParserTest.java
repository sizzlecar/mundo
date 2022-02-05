package com.bluslee.mundo.xml.test.base;

import com.bluslee.mundo.xml.XmlParserImpl;
import com.bluslee.mundo.xml.XmlSchema;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.bluslee.mundo.xml.test.XmlProcessor;
import com.google.common.io.CharStreams;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * BaseXmlParserTest.
 *
 * @author carl.che
 */
public class BaseXmlParserTest extends XmlProcessor {

    public BaseXmlParserTest() {
        super("/mundo.cfg.xml");
    }

    @Test
    public void fromXMLInputStreamTest() {
        BaseXmlParser baseXmlParser = new XmlParserImpl();
        InputStream resourceAsStream = getClass().getResourceAsStream("/mundo.cfg.xml");
        Object fromXML = baseXmlParser.fromXML(resourceAsStream);
        XmlSchema xmlSchema = getXmlSchema();
        Assert.assertEquals(xmlSchema, fromXML);
    }

    @Test
    public void fromXMLStringTest() throws IOException {
        BaseXmlParser baseXmlParser = new XmlParserImpl();
        InputStream resourceAsStream = getClass().getResourceAsStream("/mundo.cfg.xml");
        String xmlStr = CharStreams.toString(new InputStreamReader(resourceAsStream));
        Object fromXML = baseXmlParser.fromXML(xmlStr);
        XmlSchema xmlSchema = getXmlSchema();
        Assert.assertEquals(xmlSchema, fromXML);
    }

    @Test
    public void fromXMLFileTest() throws URISyntaxException {
        BaseXmlParser baseXmlParser = new XmlParserImpl();
        XmlSchema xmlSchema = getXmlSchema();
        URL resource = getClass().getResource("/mundo.cfg.xml");
        File file = Paths.get(resource.toURI()).toFile();
        Object fromXML = baseXmlParser.fromXML(file);
        Assert.assertEquals(xmlSchema, fromXML);
    }

    @Test
    public void toXmlTest() throws IOException {
        BaseXmlParser baseXmlParser = new XmlParserImpl();
        XmlSchema xmlSchema = getXmlSchema();
        //String toXMLStr = baseXmlParser.toXML(xmlSchema);
        InputStream resourceAsStream = getClass().getResourceAsStream("/mundo.cfg.xml");
        //String xmlStr = CharStreams.toString(new InputStreamReader(resourceAsStream));
        //todo
        //Assert.assertEquals(toXMLStr, xmlStr);
    }

    @Test
    public void toXmlFileTest() throws URISyntaxException, FileNotFoundException {
        XmlSchema xmlSchema = getXmlSchema();
        URL resource = getClass().getResource("/mundo.cfg.xml");
        String path = Paths.get(resource.toURI()).toAbsolutePath().toString() + ".export";
        BaseXmlParser baseXmlParser = new XmlParserImpl();
        baseXmlParser.toXML(xmlSchema, new FileOutputStream(path));
        Assert.assertTrue(new File(path).exists());
    }

}
