package com.bluslee.mundo.xml.base;

/**
 * 一些共通的常量.
 * @author carl.che
 */
public abstract class XmlConstants {

    /**
     * 配置属性名称.
     */
    public static class ConfigKey {
        /**
         * XML配置文件路径默认属性名.
         */
        public static final String XML_PATH_CONFIG_NAME = "mundo.xml.xml-path";

        /**
         * XML字符串配置属性名.
         */
        public static final String XML_STR_CONFIG_NAME = "mundo.xml.xml-str";

        /**
         * XML配置包名.
         */
        public static final String XML_PACKAGE_NAME = "mundo.xml.xml-package-name";

        /**
         * XML对应XSD文件路径.
         */
        public static final String XSD_PATH = "/mundo-1.0.0.xsd";
    }

}
