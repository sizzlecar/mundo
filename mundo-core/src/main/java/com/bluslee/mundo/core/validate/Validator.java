package com.bluslee.mundo.core.validate;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.configuration.RepositoryFactory;
import com.bluslee.mundo.core.exception.MundoException;

/**
 * Validator接口，提供校验服务.
 * @author carl.che
 */
public interface Validator {

    /**
     * 校验指定的对象，如果校验不成功则抛出异常.
     * @param configuration 配置
     * @param validateModel 待校验对象
     * @param <T> 待校验对象的类型
     * @throws MundoException 如果校验出现问题则抛出异常
     */
    <T> void validate(Configuration configuration, T validateModel) throws MundoException;

    /**
     * 根据生命周期判断是否需要校验.
     * @param lifeCycle build的哪一阶段
     * @return true 需要校验，false 不需要校验
     */
    boolean match(RepositoryFactory.LifeCycle lifeCycle);

    /**
     * 验证顺序，升序.
     * @return 顺序，越小优先级越高
     */
    int order();
}
