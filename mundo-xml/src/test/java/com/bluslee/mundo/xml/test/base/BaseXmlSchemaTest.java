package com.bluslee.mundo.xml.test.base;

import com.bluslee.mundo.xml.base.BaseXmlSchema;
import org.junit.Assert;
import org.junit.Test;

/**
 * BaseXmlSchemaTest.
 *
 * @author carl.che
 */
public class BaseXmlSchemaTest {

    @Test
    public void test() {
        BaseXmlSchema baseXmlSchema = new BaseXmlSchema() {
        };
        baseXmlSchema.setId("test01");
        baseXmlSchema.setName("test01");
        BaseXmlSchema baseXmlSchema2 = new BaseXmlSchema() {
        };
        Object obj = new Object();
        Object nullObj = null;
        Assert.assertTrue(baseXmlSchema.equals(baseXmlSchema));
        Assert.assertNotEquals(baseXmlSchema, baseXmlSchema2);
        Assert.assertNotEquals(baseXmlSchema, obj);
        Assert.assertNotEquals(baseXmlSchema, nullObj);
    }

    @Test
    public void hashcodeTest() {
        BaseXmlSchema baseXmlSchema = new BaseXmlSchema() {
        };
        baseXmlSchema.setId("test01");
        baseXmlSchema.setName("test01");
        BaseXmlSchema baseXmlSchema2 = new BaseXmlSchema() {
        };
        Assert.assertNotEquals(baseXmlSchema.hashCode(), baseXmlSchema2.hashCode());
    }
}
