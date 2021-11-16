package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.DefaultBootstrap;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseBootstrapTest.
 * @author carl.che
 * @date 2021/11/9
 */
public class BaseBootstrapTest {

    @Test
    public void defaultConfiguratorTest() {
        Configurator<BaseProcessNode> defaultConfigurator = DefaultBootstrap.getInstance().defaultConfigurator();
        Assert.assertNotNull(defaultConfigurator);
        defaultConfigurator.setProperty("mundo.xml-path", "/mundo.cfg.xml");
        Repository<BaseProcessNode> repository = defaultConfigurator.build();
        Assert.assertNotNull(defaultConfigurator);
        Assert.assertEquals(1, repository.processes().size());
    }
}
