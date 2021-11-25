package com.bluslee.mundo.process;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.core.validate.ValidatorPipLineFactory;
import com.bluslee.mundo.process.utils.ReflectionUtils;
import java.util.Set;

/**
 * ValidatorPipLineFactoryImpl.
 * @author carl.che
 * @date 2021/11/24
 */
public class ValidatorPipLineFactoryImpl implements ValidatorPipLineFactory {

    private ValidatorPipLine validatorPipLine;

    @Override
    public ValidatorPipLine build(final Configuration configuration) {
        if (validatorPipLine != null) {
            return validatorPipLine;
        }
        Set<Class<? extends ValidatorPipLine>> subTypes
                = ReflectionUtils.instance().subTypes(configuration, ValidatorPipLine.class);
        if (subTypes == null || subTypes.size() == 0) {
            throw new MundoException("not find ValidatorPipLine");
        }
        subTypes.forEach(subType -> {
            try {
                validatorPipLine = subType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new MundoException("create ValidatorPipLine error");
            }
        });
        return validatorPipLine;
    }

    @Override
    public ValidatorPipLine defaultValidatorPipLine(final Configuration configuration) {
        if (validatorPipLine != null) {
            return validatorPipLine;
        }
        return build(configuration);
    }
}
