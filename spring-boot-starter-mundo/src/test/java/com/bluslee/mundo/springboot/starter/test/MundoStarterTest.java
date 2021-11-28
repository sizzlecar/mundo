package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MundoStarterTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MundoStarterTest.class)
@SpringBootApplication
public class MundoStarterTest extends XmlProcessor {

    @Autowired
    private Repository<? extends BaseProcessNode> repository;

    public MundoStarterTest() {
        super("/mundo.cfg.xml");
    }

    @Test
    public void test() {
        Assert.assertNotNull(repository);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }
}
