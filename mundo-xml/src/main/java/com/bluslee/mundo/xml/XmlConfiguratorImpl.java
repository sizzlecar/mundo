package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.xml.base.XmlParser;

/**
 * @author carl.che
 * @date 2021/11/8
 * @description XmlConfiguratorImpl
 */
public class XmlConfiguratorImpl extends XmlConfigurator<BaseProcessNode>{

    public XmlConfiguratorImpl(XmlParser xmlParser) {
        super(xmlParser);
    }
}
