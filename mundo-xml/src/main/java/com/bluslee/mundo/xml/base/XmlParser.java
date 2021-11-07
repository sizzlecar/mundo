package com.bluslee.mundo.xml.base;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author carl.che
 * @date 2021/11/7
 * @description XML解析器，提供从XML2bean, bean2XML的功能呢
 */
public interface XmlParser {

    /**
     * 解析XML字符串转化为相应的bean
     * @param xml xml 字符串
     * @return 对应的bean
     */
    Object fromXML(String xml);

    /**
     * 解析XML InputStream转化为相应的bean
     * @param input xml InputStream
     * @return 对应的bean
     */
    Object fromXML(InputStream input);

    /**
     * 解析XML File转化为相应的bean
     * @param file xml File
     * @return 对应的bean
     */
    Object fromXML(File file);

    /**
     * 将bean转化为xml
     * @param obj 待转换的bean
     * @return 对应的xml字符串
     */
    String toXML(Object obj);

    /**
     * 将bean转换为对应xml OutputStream
     * @param obj 待转换的bean
     * @param out 对应的xml OutputStream
     */
    void toXML(Object obj, OutputStream out);
}
