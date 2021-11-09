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
 * @author carl.che
 * @date 2021/11/2
 * @description Executer
 */
public abstract class BaseExecutor implements Execute {

    private final MemberAccess memberAccess = new AbstractMemberAccess() {
        @Override
        public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
            int modifiers = member.getModifiers();
            return Modifier.isPublic(modifiers);
        }
    };

    private final OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this, memberAccess, new DefaultClassResolver(),
            new DefaultTypeConverter());

    @Override
    public <T, E> T executeExpression(E expression, Map<String, Object> parameterMap) {
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
