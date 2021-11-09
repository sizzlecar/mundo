package com.bluslee.mundo.xml.base;

import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description BaseXmlParser
 */
public abstract class BaseXmlParser implements XmlParser {

    private final XStream xStream;

    public BaseXmlParser(XStream xStream) {
        this.xStream = xStream;
    }

    @Override
    public Object fromXML(String xml) {
        return xStream.fromXML(xml);
    }

    @Override
    public Object fromXML(InputStream input) {
        return xStream.fromXML(input);
    }

    @Override
    public Object fromXML(File file) {
        return xStream.fromXML(file);
    }

    @Override
    public String toXML(Object obj) {
        return xStream.toXML(obj);
    }

    @Override
    public void toXML(Object obj, OutputStream out) {
        xStream.toXML(obj, out);
    }
}
