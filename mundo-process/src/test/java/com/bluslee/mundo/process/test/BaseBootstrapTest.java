package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.process.Bootstrap;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseBootstrapTest.
 * @author carl.che
 */
public class BaseBootstrapTest extends XmlProcessor {

    private static final String FILE_PATH = "/mundo.cfg.xml";

    public BaseBootstrapTest() {
        super(FILE_PATH);
    }

    @Test
    public void defaultConfiguratorTest() {
        /*RepositoryFactory<BaseProcessNode, byte[], XmlSchema> defaultConfigurator = Bootstrap.getInstance().defaultRepositoryBuilder();
        Assert.assertNotNull(defaultConfigurator);
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty("mundo.xml-path", "/mundo.cfg.xml");*/
        /*Repository<BaseProcessNode> repository = defaultConfigurator.build();
        Assert.assertNotNull(defaultConfigurator);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());*/
    }
}
