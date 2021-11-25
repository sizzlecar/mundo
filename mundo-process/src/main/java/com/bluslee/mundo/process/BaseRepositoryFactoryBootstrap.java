package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.process.base.BaseProcessNode;

/**
 * BaseRepositoryFactoryBootstrap.
 * @author carl.che
 */
public interface BaseRepositoryFactoryBootstrap {

    <N extends BaseProcessNode, T, L> RepositoryFactory<N, T, L> build(Configuration configuration);
}
