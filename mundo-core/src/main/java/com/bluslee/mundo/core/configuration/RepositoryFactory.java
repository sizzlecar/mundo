package com.bluslee.mundo.core.configuration;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;

/**
 * 配置器接口，提供根据配置build出{@link Repository}的服务.
 *
 * @param <N> 流程内元素必须是{@link BaseProcessNode}类型
 * @param <T> 加载后的资源类型
 * @param <L> 解析后的资源类型
 * @see BaseProcessNode
 * @see Repository
 * @author carl.che
 */
public interface RepositoryFactory<N extends BaseProcessNode, T, L> {

    /**
     * 根据配置加载对应的资源，如果有异常需要抛出异常.
     * @param configuration 配置
     * @return 返回加载后对应的资源
     */
    T load(Configuration configuration);

    /**
     * 根据配置，以及加载后的资源，进行解析，如果有异常需要抛出异常.
     * @param configuration 配置
     * @param t 加载后的资源
     * @return 解析后的资源
     */
    L parse(Configuration configuration, T t);

    /**
     * 根据配置，以及解析后的资源，build出对应的Repository.
     * @param configuration 配置
     * @param l 解析后的资源
     * @return 对应的 Repository
     */
    Repository<N> build(Configuration configuration, L l);

    /**
     * 加载配置的生命周期.
     */
    enum LifeCycle {

        /**
         * 根据配置加载对应的资源.
         */
        LOAD,

        /**
         * 根据配置，以及加载后的资源，进行解析.
         */
        PARSE,

        /**
         * 根据配置，以及解析后的资源，build出对应的资源.
         */
        BUILD

    }

}
