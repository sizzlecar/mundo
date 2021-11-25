package com.bluslee.mundo.core.configuration;

import java.io.InputStream;

/**
 * 配置接口，提供跟配置相关服务.
 * @author carl.che
 * @date 2021/11/24
 */
public interface Configuration {

    /**
     * 设置属性.
     *
     * @param key   属性名
     * @param value 值
     * @return 当前配置实例
     */
    Configuration setProperty(String key, String value);

    /**
     * 根据key获取值.
     *
     * @param key 属性key
     * @return 对应的值
     */
    Object getProperty(String key);

    /**
     * 获取当前配置的模式，比如XML.
     * @return 配置模式
     */
    String getMode();

    /**
     * 从文件中读取配置.
     *
     * @param inStream 配置文件InputStream
     */
    void load(InputStream inStream);

    /**
     * 初始化，根据配置，准备好source inputStream.
     */
    void init();

    /**
     * 获取初始化准备好的source inputStream.
     * @return 始化准备好的source inputStream
     */
    byte[] getInitData();
}
