package com.bluslee.mundo.core.validate;

import com.bluslee.mundo.core.configuration.Configuration;
import java.util.List;

/**
 * Validator流水线，提供从不同方向进行进行校验的服务.
 *
 * @author carl.che
 * @date 2021/11/24
 */
public interface ValidatorPipLine {

    /**
     * 向流水线添加validator.
     * @param validator 待加入的validator
     */
    void addValidator(Validator validator);

    /**
     * 删除指定的validator.
     * @param validator 待删除的validator
     */
    void removeValidator(Validator validator);

    /**
     * 获取流水线上所有的Validator.
     *
     * @return 流水线上所有的Validator
     */
    List<Validator> getValidators();

    /**
     * 使用当前流水线上的Validators以及指定的策略对当前配置进行验证.
     *
     * @param configuration    配置
     * @param validateStrategy 校验策略
     */
    default <T> void validate(final Configuration configuration,
                              final ValidateStrategy<T> validateStrategy,
                              final T model) {
        validateStrategy.validateStrategy(configuration, this, model);
    }

    @FunctionalInterface
    interface ValidateStrategy<T> {

        /**
         * 多个Validator的校验策略.
         *
         * @param configuration    配置
         * @param validatorPipLine 校验流水线
         */
        void validateStrategy(Configuration configuration, ValidatorPipLine validatorPipLine, T model);

        /**
         * 默认的验证策略，依次校验.
         * @return 默认的验证策略
         */
        default ValidateStrategy<T> defaultValidateStrategy() {
            return (configuration, validatorPipLine, model) -> validatorPipLine.getValidators().forEach(validator -> validator.validate(configuration, model));
        }

    }

}
