package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;

/**
 * ValidatorPipLineFactoryImpl.
 * @author carl.che
 */
public class ValidatorPipLineFactoryImpl implements ValidatorPipLineFactory {

    private final ValidatorPipLine validatorPipLine;

    public ValidatorPipLineFactoryImpl() {
        this.validatorPipLine = new ValidatorPipLineImpl();
    }

    @Override
    public ValidatorPipLine build(final Configuration configuration) {
        return validatorPipLine;
    }

    @Override
    public ValidatorPipLine defaultValidatorPipLine(final Configuration configuration) {
        return validatorPipLine;
    }
}
