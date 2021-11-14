package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.xml.XmlConfigurator;
import com.bluslee.mundo.xml.XmlConfiguratorImpl;
import com.bluslee.mundo.xml.XmlSchema;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.*;

/**
 * @author carl.che
 * @date 2021/11/8
 * @description XmlConfiguratorImplTest
 */
public class XmlConfiguratorImplTest {

    private final XStream xStream = new XStream();
    private final BaseXmlParser baseXmlParser = new BaseXmlParser(xStream) {};
    private final XmlConfigurator<BaseProcessNode> xmlConfigurator = new XmlConfiguratorImpl(baseXmlParser);
    private final Map<String, List<Integer>> expectProcess = new HashMap<String, List<Integer>>(){{
        put("process-001", Arrays.asList(0, 1));
        put("process-002", Collections.singletonList(0));
    }};

    @Test
    public void XmlConfiguratorImplBuildTest(){
        InputStream mundoXmlStream = XmlSchemaTest.class.getResourceAsStream("/mundo.cfg.xml");
        xStream.processAnnotations(XmlSchema.class);
        xStream.allowTypesByWildcard(new String[] {
                "com.bluslee.mundo.**"
        });
        Repository<BaseProcessNode> repository = xmlConfigurator.inputStream(mundoXmlStream).build();
        Set<ProcessEngine<BaseProcessNode>> processes = repository.processes();
        //mundo.cfg.xml 配置了3个流程
        Assert.assertThat(processes.size(), Matchers.is(3));
        //检查流程id,版本号
        expectProcess.forEach((id, versions) -> {
            versions.forEach(version -> {
                ProcessEngine<BaseProcessNode> actualProcess = repository.getProcess(id, version);
                Assert.assertEquals(id, actualProcess.getId());
                Assert.assertEquals(version, actualProcess.getVersion());
            });
        });

    }

}
