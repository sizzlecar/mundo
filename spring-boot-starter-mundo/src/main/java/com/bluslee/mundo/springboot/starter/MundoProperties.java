package com.bluslee.mundo.springboot.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MundoProperties.
 * @author carl.che
 */
@ConfigurationProperties(prefix = "mundo")
public class MundoProperties {

    /**
     * 是否加载mundo, true 加载，false 不加载.
     */
    private Boolean enabled;

    /**
     * xml 相关配置.
     */
    private Xml xml;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(final Xml xml) {
        this.xml = xml;
    }

    public static class Xml {

        /**
         * xml path.
         */
        private String xmlPath;

        /**
         * 各种实现位于的包名.
         */
        private String xmlPackageName = "com.bluslee.mundo.xml";

        public String getXmlPath() {
            return xmlPath;
        }

        public void setXmlPath(final String xmlPath) {
            this.xmlPath = xmlPath;
        }

        public String getXmlPackageName() {
            return xmlPackageName;
        }

        public void setXmlPackageName(final String xmlPackageName) {
            this.xmlPackageName = xmlPackageName;
        }
    }
}
