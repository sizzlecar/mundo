package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 *  默认配置器.
 * @author carl.che
 */
public class XmlRepositoryFactoryImpl extends XmlRepositoryFactory<BaseProcessNode> {

    public XmlRepositoryFactoryImpl() {
        super(new XmlParserImpl());
    }
}
