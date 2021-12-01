package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * BaseRepositoryFactoryBootstrap.
 * @author carl.che
 */
public interface BaseRepositoryFactoryBootstrap {

    /**
     * Repository factory booter.
     * @param configuration 配置
     * @param <N> 流程内元素必须是BaseProcessNode类型
     * @param <T> 加载后的资源类型
     * @param <L> 解析后的资源类型
     * @return 根据配置build出的 RepositoryFactory
     */
    <N extends BaseProcessNode, T, L> RepositoryFactory<N, T, L> build(Configuration configuration);
}
