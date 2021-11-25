package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.Bootstrap;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BaseBootstrapTest.
 * @author carl.che
 */
public class BaseMainBootstrapTest extends XmlProcessor {

    private static final String FILE_PATH = "/mundo.cfg.xml";

    public BaseMainBootstrapTest() {
        super(FILE_PATH);
    }

    @Test
    public void buildTest() {
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty("mundo.xml-path", FILE_PATH);
        Repository<BaseProcessNode> repository = Bootstrap.getInstance().build(configuration);
        Assert.assertNotNull(repository);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }
}
