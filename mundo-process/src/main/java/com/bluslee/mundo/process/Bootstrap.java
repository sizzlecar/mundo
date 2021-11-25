package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;
import com.bluslee.mundo.process.utils.ReflectionUtils;

import java.util.Set;

/**
 * Bootstrap 默认实现.
 *
 * @author carl.che
 */
public final class Bootstrap implements BaseBootstrap {

    private static volatile BaseBootstrap bootstrap;

    private final ValidatorPipLineFactory validatorPipLineFactory = new ValidatorPipLineFactoryImpl();

    private Bootstrap() {
    }

    public static BaseBootstrap getInstance() {
        if (bootstrap == null) {
            synchronized (Bootstrap.class) {
                if (bootstrap == null) {
                    bootstrap = new Bootstrap();
                }
            }
        }
        return bootstrap;
    }

    @Override
    public <N extends BaseProcessNode> Repository<N> build(final Configuration configuration) {
        //根据配置模式，扫描对应包下的RepositoryBuilder，ValidatorPipLineImpl，
        ValidatorPipLine build = validatorPipLineFactory.build(configuration);
        Set<Class<? extends RepositoryFactory>> classes = ReflectionUtils.instance().subTypes(configuration, RepositoryFactory.class);
        return null;

    }

}
