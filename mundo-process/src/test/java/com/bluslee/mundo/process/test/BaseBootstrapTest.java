package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.DefaultBootstrap;
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
 * @date 2021/11/9
 */
public class BaseBootstrapTest extends XmlProcessor {

    private static final String FILE_PATH = "/mundo.cfg.xml";

    public BaseBootstrapTest() {
        super(FILE_PATH);
    }

    @Test
    public void defaultConfiguratorTest() {
        Configurator<BaseProcessNode> defaultConfigurator = DefaultBootstrap.getInstance().defaultConfigurator();
        Assert.assertNotNull(defaultConfigurator);
        defaultConfigurator.setProperty("mundo.xml-path", "/mundo.cfg.xml");
        Repository<BaseProcessNode> repository = defaultConfigurator.build();
        Assert.assertNotNull(defaultConfigurator);
        List<XmlSchema.ProcessSchema> dom4jProcessSchemas = getProcessSchemas();
        Map<List<String>, List<XmlSchema.ProcessSchema>> expectDistinctProcessSchemas = dom4jProcessSchemas
                .stream()
                .collect(Collectors.groupingBy(processSchema -> Arrays.asList(processSchema.getId(), processSchema.getVersion().toString())));
        Assert.assertEquals(expectDistinctProcessSchemas.size(), repository.processes().size());
    }
}
