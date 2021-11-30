package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.validate.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import java.util.Objects;
import java.util.Set;

/**
 * 常规校验.
 * @author carl.che
 */
public class GeneralValidator implements Validator {

    private final jakarta.validation.Validator validation;

    private final RepositoryFactory.LifeCycle matchLifeCycle;

    public GeneralValidator() {
        this.validation = Validation.buildDefaultValidatorFactory().getValidator();
        this.matchLifeCycle = RepositoryFactory.LifeCycle.PARSE;
    }

    @Override
    public <T> void validate(final Configuration configuration, final T validateModel) throws MundoException {
        Set<ConstraintViolation<T>> violationSet = validation.validate(validateModel);
        if (violationSet != null && violationSet.size() > 0) {
            String errorMsg = "";
            for (ConstraintViolation<T> constraintViolation : violationSet) {
                String template = constraintViolation.getMessageTemplate();
                if (template != null) {
                    errorMsg = template;
                    break;
                }
            }
            throw new MundoException(errorMsg);
        }
    }

    @Override
    public boolean match(final RepositoryFactory.LifeCycle lifeCycle) {
        return Objects.equals(matchLifeCycle, lifeCycle);
    }

    @Override
    public int order() {
        return 5;
    }
}
