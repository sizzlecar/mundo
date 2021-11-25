package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.RepositoryBuilder;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * mundo Bootstrap.
 *
 * @author carl.che
 * @param <N> 流程图中基本类型
 */
public interface Bootstrap<N extends BaseProcessNode> {

    /**
     * 寻找默认的配置器.
     *
     * @return 默认的配置器
     */
    RepositoryBuilder<N> defaultConfigurator();

    /**
     * 根据名字寻找配置器.
     *
     * @param name 配置器名称
     * @return 对应的配置器
     */
    RepositoryBuilder<N> getConfigurator(String name);

    /**
     * 根据类型寻找配置器.
     *
     * @param clazz 配置器类型
     * @return 对应的配置器
     */
    <T> RepositoryBuilder<N> getConfigurator(Class<T> clazz);

}
