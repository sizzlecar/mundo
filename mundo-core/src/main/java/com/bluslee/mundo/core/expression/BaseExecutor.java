package com.bluslee.mundo.core.expression;

import com.bluslee.mundo.core.exception.MundoException;
import ognl.AbstractMemberAccess;
import ognl.DefaultTypeConverter;
import ognl.MemberAccess;
import ognl.OgnlContext;
import ognl.Ognl;
import ognl.OgnlException;
import ognl.DefaultClassResolver;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * {@link Execute} 抽象实现类，使用了OGNL作为默认实现.
 *
 * @see Execute
 * @author carl.che
 */
public abstract class BaseExecutor implements Execute {

    private final MemberAccess memberAccess = new AbstractMemberAccess() {
        @Override
        public boolean isAccessible(final Map context, final Object target, final Member member, final String propertyName) {
            int modifiers = member.getModifiers();
            return Modifier.isPublic(modifiers);
        }
    };

    private final OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this, memberAccess, new DefaultClassResolver(),
            new DefaultTypeConverter());

    /**
     * 解析表达式，并根据参数执行得到结果.
     *
     * @param expression 表达式
     * @param parameterMap 参数map
     * @param <T> 返回值类型
     * @param <E> 表达式类型
     * @return 表达式 + 参数 执行得到的结果
     */
    @Override
    public <T, E> T executeExpression(final E expression, final Map<String, Object> parameterMap) {
        context.setValues(parameterMap);
        T val = null;
        try {
            val = (T) Ognl.getValue(Ognl.parseExpression(expression.toString()), context, context.getRoot());
        } catch (OgnlException e) {
            throw new MundoException("解析表达式发生错误", e);
        }
        return val;
    }
}
