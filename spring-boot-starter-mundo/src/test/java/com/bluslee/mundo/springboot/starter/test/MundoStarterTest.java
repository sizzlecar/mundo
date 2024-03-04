package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.springboot.starter.MundoAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MundoStarterTest.
 * @author carl.che
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MundoAutoConfiguration.class)
@SpringBootApplication
public class MundoStarterTest {

    @Test
    public void noTest() {

    }

}
