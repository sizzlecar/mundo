package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * 读取配置mundo.enabled == false的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "enabled-false")
public class MundoAutoConfigurationEnabledFalseTest extends MundoStarterTest {

    @Autowired(required = false)
    private Repository repository;

    @Test
    public void mundoFactoryTest() {
        Assertions.assertNull(repository);
    }

}
