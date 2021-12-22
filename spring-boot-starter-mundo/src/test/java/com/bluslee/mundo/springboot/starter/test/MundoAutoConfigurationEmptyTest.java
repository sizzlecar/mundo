package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.process.base.BaseProcessNode;
import com.bluslee.mundo.core.process.base.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 读取没有配置mundo的配置文件单元测试.
 * @author carl.che
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MundoPropertiesEnabledTrueTest.class)
@SpringBootApplication
@ActiveProfiles(value = "empty")
public class MundoAutoConfigurationEmptyTest {

    @Autowired(required = false)
    private Repository<? extends BaseProcessNode> repository;

    @Test
    public void test() {
        Assert.assertNull(repository);
    }
}
