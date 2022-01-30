package com.bluslee.mundo.core.test.expression;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.core.expression.BaseExecutor;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * BaseExecutorTest.
 * @author carl.che
 */
public class BaseExecutorTest {

    private final BaseExecutor baseExecutor = new BaseExecutor() { };

    @Test
    public void executeExpressionTest() {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("approved", true);
        paraMap.put("numA", 150);
        paraMap.put("numB", 160);
        paraMap.put("max", 1000);
        String expression1 = "#approved";
        String expression2 = "#numA > #numB";
        String expression3 = "#numB < #max";
        String expression4 = "#foo.bar";
        Boolean e1Res = baseExecutor.executeExpression(expression1, paraMap);
        Assert.assertTrue(e1Res);
        Boolean e2Res = baseExecutor.executeExpression(expression2, paraMap);
        Assert.assertFalse(e2Res);
        Boolean e3Res = baseExecutor.executeExpression(expression3, paraMap);
        Assert.assertTrue(e3Res);
        Assert.assertThrows(MundoException.class, () -> baseExecutor.executeExpression(expression4, paraMap));
    }

}
