package com.bluslee.mundo.core.configuration;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;

import java.io.InputStream;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description Configurator 配置器接口定义
 */
public interface Configurator<N extends BaseProcessNode> {

    /**
     * 设置属性
     *
     * @param key   属性名
     * @param value 值
     */
    void setProperty(String key, String value);

    /**
     * 根据key获取值
     *
     * @param key 属性key
     * @return 对应的值
     */
    Object getProperty(String key);

    /**
     * 从文件中读取配置
     *
     * @param inStream 配置文件InputStream
     */
    void load(InputStream inStream);

    /**
     * 根据配置构建出流程图BaseProcess的示例
     *
     * @return Repository 实例
     */
    Repository<N> build();


}
