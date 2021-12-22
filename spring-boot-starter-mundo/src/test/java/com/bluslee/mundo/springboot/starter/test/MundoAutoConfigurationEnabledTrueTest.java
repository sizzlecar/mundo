package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.springboot.starter.MundoAutoConfiguration;
import com.bluslee.mundo.springboot.starter.MundoProperties;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 读取mundo.enabled == true的配置文件单元测试.
 * @author carl.che
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MundoPropertiesEnabledTrueTest.class)
@SpringBootApplication
@ActiveProfiles(value = "enabled-true")
public class MundoAutoConfigurationEnabledTrueTest extends XmlProcessor {

    @Autowired(required = false)
    private Repository<? extends BaseProcessNode> repository;

    @Autowired
    private MundoProperties mundoProperties;

    public MundoAutoConfigurationEnabledTrueTest() {
        super("/mundo.cfg.xml");
    }

    @Test
    public void autowiredTest() {
        Assert.assertNotNull(repository);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }

    @Test
    public void createBeanTest() {
        MundoAutoConfiguration mundoAutoConfiguration = new MundoAutoConfiguration();
        Repository<? extends BaseProcessNode> repository = mundoAutoConfiguration.createRepository(mundoProperties);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }

}
