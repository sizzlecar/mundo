package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;

/**
 * mundo Bootstrap.
 *
 * @author carl.che
 */
public interface BaseMainBootstrap {

    /**
     * 根据配置获取对应的Repository.
     * @param configuration 配置
     * @param <N> N必须是BaseProcessNode类型
     * @return Repository
     */
    <N extends BaseProcessNode> Repository<N> build(Configuration configuration);

}
