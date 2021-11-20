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
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * XmlConfiguratorImplTest.
 * @author carl.che
 */
public class XmlConfiguratorImplTest extends XmlProcessor {

    private static final String FILE_PATH = "/mundo.cfg.xml";

    private final XStream xStream = new XStream();

    private final BaseXmlParser baseXmlParser = new BaseXmlParser(xStream) { };

    private final XmlConfigurator<BaseProcessNode> xmlConfigurator = new XmlConfiguratorImpl(baseXmlParser);

    public XmlConfiguratorImplTest() {
        super(FILE_PATH);
    }

    @Test
    public void xmlConfiguratorImplBuildTest() {
        InputStream mundoXmlStream = getClass().getResourceAsStream("/mundo.cfg.xml");
        xStream.processAnnotations(XmlSchema.class);
        xStream.allowTypesByWildcard(new String[] {"com.bluslee.mundo.**"});
        Repository<BaseProcessNode> repository = xmlConfigurator.inputStream(mundoXmlStream).build();
        Set<ProcessEngine<BaseProcessNode>> processes = repository.processes();
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertThat(processes.size(), Matchers.is(expectDistinctProcessSchemas.size()));
        //检查流程id,版本号
        expectDistinctProcessSchemas.forEach((keys, val) -> {
            String id = keys.get(0);
            String versionStr = keys.get(1);
            Integer version = Integer.parseInt(versionStr);
            ProcessEngine<BaseProcessNode> actualProcess = repository.getProcess(id, version);
            Assert.assertEquals(id, actualProcess.getId());
            Assert.assertEquals(version, actualProcess.getVersion());
        });
    }

}
