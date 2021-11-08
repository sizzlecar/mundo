package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configurator;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * @author carl.che
 * @date 2021/11/9
 * @description Bootstrap
 */
public interface Bootstrap<N extends BaseProcessNode> {

    /**
     * 寻找默认的配置器
     * @return 默认的配置器
     */
    Configurator<N> defaultConfigurator();

    /**
     * 根据名字寻找配置器
     * @param name 配置器名称
     * @return 对应的配置器
     */
    Configurator<N> getConfigurator(String name);

    /**
     * 根据类型寻找配置器
     * @param clazz 配置器类型
     * @return 对应的配置器
     */
    <T> Configurator<N> getConfigurator(Class<T> clazz);



}
