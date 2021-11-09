package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.xml.XmlConfiguratorImpl;
import com.bluslee.mundo.xml.XmlSchema;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.thoughtworks.xstream.XStream;

/**
 * @author carl.che
 * @date 2021/11/9
 * @description BaseBootstrap
 */
public class DefaultBootstrap implements Bootstrap<BaseProcessNode> {

    private static volatile DefaultBootstrap bootstrap;
    private final XStream xStream = new XStream();
    private final Configurator<BaseProcessNode> xmlConfigurator = new XmlConfiguratorImpl(new BaseXmlParser(xStream) {
    });

    private DefaultBootstrap() {
    }


    public static DefaultBootstrap getInstance() {
        if (bootstrap == null) {
            synchronized (DefaultBootstrap.class) {
                if (bootstrap == null) {
                    bootstrap = new DefaultBootstrap();
                }
            }
        }
        return bootstrap;
    }

    @Override
    public Configurator<BaseProcessNode> defaultConfigurator() {
        xStream.processAnnotations(XmlSchema.class);
        return xmlConfigurator;
    }

    @Override
    public Configurator<BaseProcessNode> getConfigurator(String name) {
        return null;
    }

    @Override
    public <T> Configurator<BaseProcessNode> getConfigurator(Class<T> clazz) {
        return null;
    }
}
