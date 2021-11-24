package com.bluslee.mundo.core.configuration;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;

/**
 * 配置器接口，提供根据配置build出{@link BaseProcessNode}的服务.
 *
 * @param <N> 流程内元素必须是{@link BaseProcessNode}类型
 * @see BaseProcessNode
 * @see BaseProcessNode
 * @author carl.che
 */
public interface Configurator<N extends BaseProcessNode> extends Configuration {

    /**
     * 根据配置构建出流程图BaseProcess的示例.
     *
     * @return Repository 实例
     */
    Repository<N> build();

}
