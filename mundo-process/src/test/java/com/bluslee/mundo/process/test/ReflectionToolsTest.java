package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.process.test.reflection.AbstractSubClass;
import com.bluslee.mundo.process.test.reflection.NormalSubClass;
import com.bluslee.mundo.process.test.reflection.SuperInterface;
import com.bluslee.mundo.process.utils.ReflectionTools;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XmlParserImpl;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.bluslee.mundo.xml.base.XmlParser;
import org.junit.Assert;
import org.junit.Test;
import java.util.Set;
import java.util.HashSet;

/**
 * ReflectionToolsTest.
 * @author carl.che
 */
public class ReflectionToolsTest {

    @Test
    public void subTypesTest() {
        String packName = "com.bluslee.mundo.process.test";
        Set<Class<? extends SuperInterface>> actualClasses = ReflectionTools.instance().subTypes(packName, SuperInterface.class);
        Set<Class> expectClasses = new HashSet<>();
        expectClasses.add(NormalSubClass.class);
        expectClasses.add(AbstractSubClass.class);
        Assert.assertEquals(expectClasses, actualClasses);
    }

    @Test
    public void subTypeByConfigTest() {
        Configuration configuration = new XmlConfiguration();
        Set<Class<? extends XmlParser>> actualClasses = ReflectionTools.instance().subTypes(configuration, XmlParser.class);
        Set<Class> expectClasses = new HashSet<>();
        expectClasses.add(BaseXmlParser.class);
        expectClasses.add(XmlParserImpl.class);
        Assert.assertEquals(expectClasses, actualClasses);
    }

    @Test
    public void firstNoParamConstructorSubInstanceByConfigTest() {
        Configuration configuration = new XmlConfiguration();
        XmlParser xmlParser = ReflectionTools.instance().firstNoParamConstructorSubInstance(configuration, XmlParser.class);
        Assert.assertTrue(xmlParser instanceof XmlParserImpl);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest() {
        String packName = "com.bluslee.mundo.process.test";
        SuperInterface superInterface = ReflectionTools.instance().firstNoParamConstructorSubInstance(packName, SuperInterface.class);
        Assert.assertTrue(superInterface instanceof NormalSubClass);
    }
}
