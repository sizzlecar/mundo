package com.bluslee.mundo.xml;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.validate.Validator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import java.util.Set;

/**
 * 常规校验.
 * @author carl.che
 * @date 2021/11/24
 */
public class GeneralValidator implements Validator {

    private final jakarta.validation.Validator validation = Validation.buildDefaultValidatorFactory().getValidator();

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
    public int order() {
        return 5;
    }
}
