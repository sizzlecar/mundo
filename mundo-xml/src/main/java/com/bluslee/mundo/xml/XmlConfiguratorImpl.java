package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.xml.base.XmlParser;

/**
 *  默认配置器.
 * @author carl.che
 */
public class XmlConfiguratorImpl extends XmlConfigurator<BaseProcessNode> {

    public XmlConfiguratorImpl(final XmlParser xmlParser) {
        super(xmlParser);
    }
}
