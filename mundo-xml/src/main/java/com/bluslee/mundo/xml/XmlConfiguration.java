package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.xml.base.XmlConstants;
import java.io.InputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

/**
 * XML 配置相关.
 * @author carl.che
 */
public class XmlConfiguration implements Configuration {

    private static final String MODEL = "xml";

    /**
     * 其他配置.
     */
    private final Properties properties = new Properties();

    /**
     * 是否完成初始化，初始化的过程就是根据其他配置转化为 initInputStream.
     */
    private Boolean initFlag = false;

    /**
     * 初始化完成后的XMl对应的inputStream.
     */
    private byte[] initData;

    /**
     * setProperty.
     * @param key   属性名
     * @param value 值
     */
    @Override
    public Configuration setProperty(final String key, final String value) {
        this.properties.setProperty(key, value);
        return this;
    }

    /**
     * getProperty.
     * @param key 属性key
     * @return Object
     */
    @Override
    public Object getProperty(final String key) {
        return this.properties.getProperty(key);
    }

    /**
     * load inStream.
     * @param inStream 配置文件InputStream
     */
    @Override
    public void load(final InputStream inStream) {
        try {
            properties.load(inStream);
        } catch (IOException e) {
            throw new MundoException("读取配置文件发生异常", e);
        }
    }

    /**
     * 初始化：根据properties.getProperty(XML_PATH_CONFIG_NAME)，properties.getProperty(XML_STR_CONFIG_NAME)等配置
     * 顺序转化为initInputStream.
     */
    @Override
    public void init() {
        if (initFlag) {
            return;
        }
        Optional.ofNullable(properties.getProperty(XmlConstants.ConfigKey.XML_PATH_CONFIG_NAME))
                .ifPresent(xmlPath -> {
                    try (InputStream inputStream = getClass().getResourceAsStream(xmlPath)) {
                        byte[] data = new byte[inputStream.available()];
                        inputStream.read(data);
                        initData = data;
                    } catch (IOException | NullPointerException e) {
                        throw new MundoException("配置初始化发生错误", e);
                    }
                    initFlag = true;
                    init();
                });
        Optional.ofNullable(properties.getProperty(XmlConstants.ConfigKey.XML_STR_CONFIG_NAME))
                .ifPresent(configXmlStr -> {
                    initData = configXmlStr.getBytes(StandardCharsets.UTF_8);
                    initFlag = true;
                });
        Optional.ofNullable(initData).orElseThrow(() -> new MundoException("没有有效的配置"));
    }

    @Override
    public byte[] getInitData() {
        if (!initFlag) {
            throw new MundoException("当前配置还未完成初始化，请初始化后使用");
        }
        return initData;
    }

    @Override
    public String getMode() {
        return MODEL;
    }
}
