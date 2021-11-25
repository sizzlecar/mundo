package com.bluslee.mundo.xml;

import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.thoughtworks.xstream.XStream;

/**
 * XmlParserImpl.
 * @author carl.che
 */
public class XmlParserImpl extends BaseXmlParser {

    public XmlParserImpl() {
        super(new XStream());
    }
}
