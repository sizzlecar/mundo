package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.springboot.starter.MundoAutoConfiguration;
import com.bluslee.mundo.springboot.starter.MundoProperties;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 读取mundo.enabled == true的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "enabled-true")
public class MundoAutoConfigurationEnabledTrueTest extends MundoStarterTest {

    @Autowired(required = false)
    private Repository<? extends BaseProcessNode> repository;

    @Autowired
    private MundoProperties mundoProperties;

    private XmlProcessor xmlProcessor = new XmlProcessor("/mundo.cfg.xml");

    @Test
    public void autowiredTest() {
        Assert.assertNotNull(repository);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = xmlProcessor.getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }

    @Test
    public void createBeanTest() {
        MundoAutoConfiguration mundoAutoConfiguration = new MundoAutoConfiguration();
        Repository<? extends BaseProcessNode> repository = mundoAutoConfiguration.createRepository(mundoProperties);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = xmlProcessor.getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }

    @Test
    public void createBeanNoXmlConfigTest() {
        MundoAutoConfiguration mundoAutoConfiguration = new MundoAutoConfiguration();
        MundoProperties noXmlConfig = new MundoProperties();
        Assert.assertThrows(MundoException.class, () -> mundoAutoConfiguration.createRepository(noXmlConfig));
    }

}
