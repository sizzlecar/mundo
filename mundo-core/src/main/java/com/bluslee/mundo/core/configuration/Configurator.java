package com.bluslee.mundo.core.configuration;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import java.io.InputStream;

/**
 * 配置器接口，提供根据配置build出{@link BaseProcessNode}的服务.
 *
 * @param <N> 流程内元素必须是{@link BaseProcessNode}类型
 * @see BaseProcessNode
 * @see BaseProcessNode
 * @author carl.che
 */
public interface Configurator<N extends BaseProcessNode> {

    /**
     * 设置属性.
     *
     * @param key   属性名
     * @param value 值
     */
    void setProperty(String key, String value);

    /**
     * 根据key获取值.
     *
     * @param key 属性key
     * @return 对应的值
     */
    Object getProperty(String key);

    /**
     * 从文件中读取配置.
     *
     * @param inStream 配置文件InputStream
     */
    void load(InputStream inStream);

    /**
     * 根据配置构建出流程图BaseProcess的示例.
     *
     * @return Repository 实例
     */
    Repository<N> build();

}
