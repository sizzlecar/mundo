package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.validate.Validator;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ValidatorPipLine实现.
 * @author carl.che
 * @date 2021/11/24
 */
public class ValidatorPipLineImpl implements ValidatorPipLine {

    private final List<Validator> validators = new LinkedList<>();

    public ValidatorPipLineImpl() {
        validators.add(new XsdValidator());
        validators.add(new GeneralValidator());
    }

    @Override
    public void addValidator(final Validator validator) {
        validators.add(validator);
        List<Validator> sortValidators = validators.stream().sorted(Comparator.comparing(Validator::order)).collect(Collectors.toList());
        validators.clear();
        validators.addAll(sortValidators);
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
                             final ValidateStrategy<T> validateStrategy,
                             final T model,
                             final RepositoryFactory.LifeCycle lifeCycle) {
        validateStrategy.validateStrategy(configuration, this, model, lifeCycle);
    }
}
