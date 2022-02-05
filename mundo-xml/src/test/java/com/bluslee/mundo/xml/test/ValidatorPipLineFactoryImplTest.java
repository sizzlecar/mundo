package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.validate.ValidatorPipLine;
import com.bluslee.mundo.xml.ValidatorPipLineFactoryImpl;
import com.bluslee.mundo.xml.XmlConfiguration;
import org.junit.Assert;
import org.junit.Test;

/**
 * ValidatorPipLineFactoryImplTest.
 *
 * @author carl.che
 */
public class ValidatorPipLineFactoryImplTest {

    @Test
    public void test() {
        ValidatorPipLineFactoryImpl factory = new ValidatorPipLineFactoryImpl();
        XmlConfiguration xmlConfiguration = new XmlConfiguration();
        ValidatorPipLine validatorPipLine = factory.defaultValidatorPipLine(xmlConfiguration);
        ValidatorPipLine build = factory.build(xmlConfiguration);
        Assert.assertNotNull(build);
        Assert.assertNotNull(validatorPipLine);
    }
}
