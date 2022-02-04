package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.core.constant.LifeCycle;
import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.xml.GeneralValidator;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * GeneralValidatorTest.
 *
 * @author carl.che
 */
public class GeneralValidatorTest extends XmlProcessor {

    public GeneralValidatorTest() {
        super("/mundo.cfg.xml");
    }

    @Test
    public void matchTest() {
        GeneralValidator generalValidator = new GeneralValidator();
        boolean match = generalValidator.match(LifeCycle.PARSE);
        Assert.assertTrue(match);
    }

    @Test
    public void validateTest() {
        GeneralValidator generalValidator = new GeneralValidator();
        XmlSchema xmlSchema = new XmlSchema();
        Assert.assertThrows(MundoException.class, () -> generalValidator.validate(new XmlConfiguration(), xmlSchema));
        List<XmlSchema.ProcessSchema> processSchemas = getProcessSchemas();
        generalValidator.validate(new XmlConfiguration(), processSchemas.get(0));
    }

    @Test
    public void orderTest() {
        GeneralValidator generalValidator = new GeneralValidator();
        generalValidator.order();
    }

}
