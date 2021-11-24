package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.validate.Validator;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.google.common.collect.Lists;
import java.util.LinkedList;
import java.util.List;

/**
 * ValidatorPipLine实现.
 * @author carl.che
 * @date 2021/11/24
 */
public class ValidatorPipLineImpl implements ValidatorPipLine {

    private final List<Validator> validators = new LinkedList<>();

    @Override
    public void addValidator(final Validator validator) {
        validators.add(validator);
    }

    @Override
    public void removeValidator(final Validator validator) {
        validators.remove(validator);
    }

    @Override
    public List<Validator> getValidators() {
        return Lists.newArrayList(validators);
    }

    @Override
    public <T> void validate(final Configuration configuration,
                             final ValidatorPipLine validatorPipLine,
                             final ValidateStrategy<T> validateStrategy,
                             final T model) {
        validateStrategy.validateStrategy(configuration, validatorPipLine, model);
    }
}
