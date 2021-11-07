package com.bluslee.mundo.core.configuration;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.BaseRepository;

import java.util.Properties;

/**
 * @author carl.che
 * @date 2021/11/1
 * @description Configurator 配置器接口定义
 */
public interface Configurator {

    /**
     * 批量设置属性
     * @param properties 配置
     */
    void properties(Properties properties);

    /**
     * 设置属性
     * @param key key
     * @param value val
     */
    void setProperty(String key, String value);

    /**
     * 根据配置构建出流程图BaseProcess的示例
     *
     * @param <N> 节点类型
     * @return Repository 实例
     */
    <N extends BaseProcessNode> BaseRepository<N> build();


}
