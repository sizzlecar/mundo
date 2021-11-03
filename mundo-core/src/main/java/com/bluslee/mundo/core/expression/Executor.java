package com.bluslee.mundo.core.expression;

import ognl.*;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description Executer
 */
public class Executor implements Execute{

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
        try{
            val = (T) Ognl.getValue(Ognl.parseExpression(expression.toString()), context, context.getRoot());
        }catch (OgnlException e){
            return null;
        }
        return val;
    }
}
