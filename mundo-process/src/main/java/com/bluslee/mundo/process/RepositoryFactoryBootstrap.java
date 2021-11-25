package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.process.utils.ReflectionTools;

/**
 * RepositoryFactoryBootstrap.
 * @author carl.che
 */
public class RepositoryFactoryBootstrap implements BaseRepositoryFactoryBootstrap {

    RepositoryFactoryBootstrap() { }

    @Override
    public <N extends BaseProcessNode, T, L> RepositoryFactory<N, T, L> build(final Configuration configuration) {
        RepositoryFactory<N, T, L> repositoryFactory = ReflectionTools
                .instance()
                .firstNoParamConstructorSubInstance(configuration, RepositoryFactory.class);
        return repositoryFactory;
    }
}
