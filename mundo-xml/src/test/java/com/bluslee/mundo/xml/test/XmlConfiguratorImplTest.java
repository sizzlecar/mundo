package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.xml.XmlConfigurator;
import com.bluslee.mundo.xml.XmlConfiguratorImpl;
import com.bluslee.mundo.xml.XmlSchema;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/8
 * @description XmlConfiguratorImplTest
 */
public class XmlConfiguratorImplTest {
    private final XStream xStream = new XStream();
    private final XmlConfigurator<BaseProcessNode> xmlConfigurator = new XmlConfiguratorImpl(new BaseXmlParser(xStream) {});


    @Test
    public void buildTest(){
        InputStream mundoXmlStream = XmlSchemaTest.class.getResourceAsStream("/mundo.cfg.xml");
        xStream.processAnnotations(XmlSchema.class);
        Repository<BaseProcessNode> repository = xmlConfigurator.inputStream(mundoXmlStream).build();
        Set<ProcessEngine<BaseProcessNode>> processes = repository.processes();
        processes.forEach(process -> {
            System.out.println(process.getId());
            BaseProcessNode processNode = process.getProcessNode("START");
            System.out.println(processNode.toString());
            Map<String, Object> map = new HashMap<>();
            map.put("approve", true);
            Set<BaseProcessNode> baseProcessNodes = process.forecastProcessNode(processNode, map);
            System.out.println(baseProcessNodes.toString());
        });
    }

}
