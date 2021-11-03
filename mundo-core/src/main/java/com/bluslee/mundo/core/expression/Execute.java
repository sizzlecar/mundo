package com.bluslee.mundo.core.expression;

import java.util.Map;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description Execute
 */
public interface Execute {

    /**
     * 执行表达式并返回结果
     * @param expression 表达式
     * @param parameterMap 参数map
     * @param <T> 返回值类型
     * @param <E> 表达式类型
     * @return 执行结果
     */
    <T, E> T executeExpression(E expression, Map<String, Object> parameterMap);
}
