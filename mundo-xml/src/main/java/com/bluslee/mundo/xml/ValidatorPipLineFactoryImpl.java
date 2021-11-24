package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;

/**
 * ValidatorPipLineFactoryImpl.
 * @author carl.che
 * @date 2021/11/24
 */
public class ValidatorPipLineFactoryImpl implements ValidatorPipLineFactory {

    private final ValidatorPipLine validatorPipLine;

    public ValidatorPipLineFactoryImpl() {
        validatorPipLine = new ValidatorPipLineImpl();
        validatorPipLine.addValidator(new XsdValidator());
        validatorPipLine.addValidator(new GeneralValidator());
    }

    @Override
    public ValidatorPipLine build() {
        return validatorPipLine;
    }
}
