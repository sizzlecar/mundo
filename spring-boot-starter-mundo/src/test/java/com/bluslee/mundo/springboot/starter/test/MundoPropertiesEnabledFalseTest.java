package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.springboot.starter.MundoProperties;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * 读取配置mundo.enabled == false的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "enabled-false")
public class MundoPropertiesEnabledFalseTest extends MundoStarterTest {

    @Autowired(required = false)
    private MundoProperties actualProperties;

    @Test
    public void parsePropertiesTest() {
        Assert.assertNull(actualProperties);
    }

}
