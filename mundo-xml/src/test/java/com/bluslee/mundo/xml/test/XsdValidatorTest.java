package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.constant.LifeCycle;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XsdValidator;
import org.junit.Assert;
import org.junit.Test;

/**
 * XsdValidatorTest.
 *
 * @author carl.che
 */
public class XsdValidatorTest {

    @Test
    public void matchTest() {
        XsdValidator xsdValidator = new XsdValidator();
        boolean match = xsdValidator.match(LifeCycle.LOAD);
        Assert.assertTrue(match);
    }

    @Test
    public void validatorTest() {
        XsdValidator xsdValidator = new XsdValidator();
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty("mundo.xml.xml-path", "/mundo.cfg.xml");
        configuration.init();
        //未抛出异常就视为正常
        xsdValidator.validate(configuration, null);
    }

    @Test
    public void validatorTest2() {
        XsdValidator xsdValidator = new XsdValidator();
        Configuration configuration = new XmlConfiguration();
        configuration.setProperty("mundo.xml.xml-path", "/mundo.error.cfg.xml");
        configuration.init();
        Assert.assertThrows(MundoException.class, () -> xsdValidator.validate(configuration, null));
    }

    @Test
    public void orderTest() {
        //如何判断是正确的 todo
        XsdValidator xsdValidator = new XsdValidator();
        xsdValidator.order();
    }
}
