package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * ProcessSchemaTest.
 *
 * @author carl.che
 */
public class ProcessSchemaTest extends XmlProcessor {

    public ProcessSchemaTest() {
        super("/mundo.cfg.xml");
    }

    @Test
    public void equalsTest() {
        List<XmlSchema.ProcessSchema> processSchemas = getProcessSchemas();
        XmlSchema.ProcessSchema processSchema = processSchemas.get(0);
        XmlSchema.ProcessSchema processSchema1 = new XmlSchema.ProcessSchema();
        Object obj = new Object();
        Object nullObj = null;
        Assert.assertNotEquals(processSchema, processSchema1);
        Assert.assertNotEquals(processSchema, obj);
        Assert.assertNotEquals(processSchema, nullObj);
    }
}
