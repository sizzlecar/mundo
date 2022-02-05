package com.bluslee.mundo.xml.test;

import com.bluslee.mundo.xml.XmlSchema;
import org.junit.Assert;
import org.junit.Test;

/**
 * ProcessLinkSchemaTest.
 *
 * @author carl.che
 */
public class ProcessLinkSchemaTest {

    @Test
    public void equalsTest() {
        XmlSchema.ProcessLinkSchema linkSchema = new XmlSchema.ProcessLinkSchema("link01", "link01", "s1", "t1", null);
        XmlSchema.ProcessLinkSchema linkSchema1 = new XmlSchema.ProcessLinkSchema("link02", "link02", "s2", "t2", null);
        Assert.assertEquals(linkSchema, linkSchema);
        Assert.assertNotEquals(linkSchema, linkSchema1);
        Assert.assertNotEquals(linkSchema, null);
        Assert.assertNotEquals(linkSchema, new Object());
    }

    @Test
    public void hashcodeTest() {
        XmlSchema.ProcessLinkSchema linkSchema = new XmlSchema.ProcessLinkSchema("link01", "link01", "s1", "t1", null);
        XmlSchema.ProcessLinkSchema linkSchema1 = new XmlSchema.ProcessLinkSchema("link02", "link02", "s2", "t2", null);
        Assert.assertNotEquals(linkSchema.hashCode(), linkSchema1.hashCode());
    }
}
