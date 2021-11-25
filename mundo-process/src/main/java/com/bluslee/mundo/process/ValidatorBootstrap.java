package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;
import com.bluslee.mundo.process.utils.ReflectionTools;

/**
 * ValidatorBootstrap,根据配置build出ValidatorPipLineFactory.
 * @author carl.che
 */
public class ValidatorBootstrap implements BaseValidatorBootstrap {

    private ValidatorPipLineFactory validatorPipLineFactory;

    ValidatorBootstrap() { }

    @Override
    public ValidatorPipLineFactory build(final Configuration configuration) {
        return ReflectionTools.instance()
                .firstNoParamConstructorSubInstance(configuration, ValidatorPipLineFactory.class);
    }
}
