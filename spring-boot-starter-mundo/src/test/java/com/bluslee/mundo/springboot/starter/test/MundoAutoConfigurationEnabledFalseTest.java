package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

/**
 * 读取mundo.enabled == false的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "enabled-false")
public class MundoAutoConfigurationEnabledFalseTest extends MundoStarterTest {

    @Autowired(required = false)
    private Repository<? extends BaseProcessNode> repository;

    @Test
    public void test() {
        Assert.assertNull(repository);
    }
}
