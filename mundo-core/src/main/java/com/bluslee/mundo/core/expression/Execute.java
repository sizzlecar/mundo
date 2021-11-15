package com.bluslee.mundo.core.expression;

import java.util.Map;

/**
 * 执行器接口，提供根据表达式 + 参数 执行得出结果的服务.
 *
 * @author carl.che
 */
public interface Execute {

    /**
     * 执行表达式并返回结果.
     *
     * @param expression   表达式
     * @param parameterMap 参数map
     * @param <T>          返回值类型
     * @param <E>          表达式类型
     * @return 执行结果
     */
    <T, E> T executeExpression(E expression, Map<String, Object> parameterMap);
}
