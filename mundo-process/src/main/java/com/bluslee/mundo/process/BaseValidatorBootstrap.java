package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;

/**
 * BaseValidatorBootstrap.
 * @author carl.che
 */
public interface BaseValidatorBootstrap {

    /**
     * build ValidatorPipLineFactory.
     * @return ValidatorPipLineFactory
     */
    ValidatorPipLineFactory build(Configuration configuration);
}
