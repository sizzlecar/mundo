package com.bluslee.mundo.core.validate;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.exception.MundoException;

/**
 * Validator接口，提供校验服务.
 * @author carl.che
 * @date 2021/11/24
 */
public interface Validator {

    /**
     * 校验指定的对象，如果校验不成功则抛出异常.
     * @param validateModel 待校验对象
     * @param <T> 待校验对象的类型
     * @throws MundoException 如果校验出现问题则抛出异常
     */
    <T> void validate(Configuration configuration, T validateModel) throws MundoException;

    /**
     * 验证顺序，升序.
     * @return
     */
    int order();
}
