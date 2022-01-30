package com.bluslee.mundo.core.validate;

import com.bluslee.mundo.core.configuration.Configuration;

/**
 * ValidatorPipLineFactory.
 * @author carl.che
 */
public interface ValidatorPipLineFactory {

    /**
     * build ValidatorPipLine.
     * @param configuration 配置
     * @return ValidatorPipLine
     */
    ValidatorPipLine build(Configuration configuration);

    /**
     * return defaultValidatorPipLine.
     * @param configuration 配置
     * @return ValidatorPipLine
     */
    ValidatorPipLine defaultValidatorPipLine(Configuration configuration);
}
