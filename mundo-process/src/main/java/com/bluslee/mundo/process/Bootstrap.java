package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.constant.LifeCycle;
import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;

/**
 * Bootstrap 默认实现.
 *
 * @author carl.che
 */
public final class Bootstrap implements BaseMainBootstrap {

    private static volatile BaseMainBootstrap bootstrap;

    private final BaseValidatorBootstrap validatorBootstrap = new ValidatorBootstrap();

    private final BaseRepositoryFactoryBootstrap repositoryFactoryBootstrap = new RepositoryFactoryBootstrap();

    private Bootstrap() {
    }

    public static BaseMainBootstrap getInstance() {
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
        RepositoryFactory<N, Object, Object> repositoryFactory = repositoryFactoryBootstrap
                .build(configuration);
        ValidatorPipLineFactory validatorPipLineFactory = validatorBootstrap.build(configuration);
        ValidatorPipLine validatorPipLine = validatorPipLineFactory.defaultValidatorPipLine(configuration);
        ValidatorPipLine.ValidateStrategy<Object> validateStrategy = (config, pipLine, model, lifeCycle) ->
                pipLine.getValidators()
                        .stream()
                        .filter(validator -> validator.match(lifeCycle))
                        .forEach(validator -> validator.validate(config, model));
        Object loadRes = repositoryFactory.load(configuration);
        validatorPipLine.validate(configuration, validateStrategy, loadRes, LifeCycle.LOAD);
        Object parseRes = repositoryFactory.parse(configuration, loadRes);
        validatorPipLine.validate(configuration, validateStrategy, parseRes, LifeCycle.PARSE);
        return repositoryFactory.build(configuration, parseRes);
    }

}
