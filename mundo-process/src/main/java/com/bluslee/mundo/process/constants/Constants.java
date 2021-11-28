package com.bluslee.mundo.process.constants;

/**
 * 一些共通的常量.
 * @author carl.che
 * @date 2021/11/24
 */
public abstract class Constants {

    /**
     * 配置属性名称.
     */
    public static class ConfigKey {

        /**
         * XML配置文件路径默认属性名.
         */
        public static final String MODE_PACKAGE_NAME = "mundo.mode.xml-package-name";

        /**
         * XML对应XSD文件路径.
         */
        public static final String XSD_PATH = "/mundo-1.0.0.xsd";

        public static class Mode {

            public static final String XML = "xml";

            public static final String XML_PACKAGE_NAME = "com.bluslee.mundo.xml";

        }

    }

}
