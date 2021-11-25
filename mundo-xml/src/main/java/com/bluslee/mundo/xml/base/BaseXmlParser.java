package com.bluslee.mundo.xml.base;

import com.bluslee.mundo.xml.XmlSchema;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * XmlParser抽象实现.
 *
 * @author carl.che
 */
public abstract class BaseXmlParser implements XmlParser {

    private final XStream xStream;

    public BaseXmlParser(final XStream xStream) {
        this.xStream = xStream;
        xStream.processAnnotations(XmlSchema.class);
        xStream.allowTypesByWildcard(new String[] {"com.bluslee.mundo.**"});
    }

    @Override
    public Object fromXML(final String xml) {
        return xStream.fromXML(xml);
    }

    @Override
    public Object fromXML(final InputStream input) {
        return xStream.fromXML(input);
    }

    @Override
    public Object fromXML(final File file) {
        return xStream.fromXML(file);
    }

    @Override
    public String toXML(final Object obj) {
        return xStream.toXML(obj);
    }

    @Override
    public void toXML(final Object obj, final OutputStream out) {
        xStream.toXML(obj, out);
    }
}
