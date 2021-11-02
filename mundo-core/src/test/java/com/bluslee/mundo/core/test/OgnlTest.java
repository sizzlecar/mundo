package com.bluslee.mundo.core.test;

import ognl.*;
import org.junit.Test;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author carl.che
 * @date 2021/11/2
 * @description OgnlTest
 * @copyright COPYRIGHT © 2014 - 2021 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 */
public class OgnlTest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Test
    public void test() throws OgnlException {
        MemberAccess memberAccess = new AbstractMemberAccess() {
            @Override
            public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
                int modifiers = member.getModifiers();
                return Modifier.isPublic(modifiers);
            }
        };
        OgnlContext context = (OgnlContext) Ognl.createDefaultContext(this, memberAccess, new DefaultClassResolver(),
                new DefaultTypeConverter());
        context.setRoot(this);
        Map<String, Object> para = new HashMap<>();
        OgnlTest ognlTest = new OgnlTest();
        ognlTest.setName("ognlTest1111");
        para.put("isApproved", true);
        para.put("ognlTest", ognlTest);
        context.put("map", para);

        Object expression = Ognl.parseExpression("#map.isApproved");
        Object result = Ognl.getValue(expression, context, context.getRoot());
        System.out.println(result);

        Object expression2 = Ognl.parseExpression("#map.ognlTest.name");
        Object result2 = Ognl.getValue(expression2, context, context.getRoot());
        System.out.println(result2);


        Object expression3 = Ognl.parseExpression("#name");
        Object result3 = Ognl.getValue(expression3, context, context.getRoot());
        System.out.println(result3);
    }

}
