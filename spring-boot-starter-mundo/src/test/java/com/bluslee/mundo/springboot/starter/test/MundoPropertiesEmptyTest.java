package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.springboot.starter.MundoProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * 读取没有配置mundo的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "empty")
public class MundoPropertiesEmptyTest extends MundoStarterTest {

    @Autowired(required = false)
    private MundoProperties actualProperties;

    @Test
    public void parsePropertiesTest() {
        Assertions.assertNull(actualProperties);
    }

}
