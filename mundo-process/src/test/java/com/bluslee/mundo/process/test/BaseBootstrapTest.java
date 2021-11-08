package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.ProcessEngine;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.process.BaseBootstrap;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author carl.che
 * @date 2021/11/9
 * @description BaseBootstrapTest
 */
public class BaseBootstrapTest {


    @Test
    public void defaultConfiguratorTest() {
        Configurator<BaseProcessNode> defaultConfigurator = BaseBootstrap.getInstance().defaultConfigurator();
        defaultConfigurator.setProperty("mundo.xml-path", "/mundo.cfg.xml");
        Repository<BaseProcessNode> repository = defaultConfigurator.build();
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
