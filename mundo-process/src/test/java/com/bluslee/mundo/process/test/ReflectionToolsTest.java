package com.bluslee.mundo.process.test;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.process.constants.Constants;
import com.bluslee.mundo.process.test.reflection.AbstractSubClass;
import com.bluslee.mundo.process.test.reflection.NormalSubClass;
import com.bluslee.mundo.process.test.reflection.SuperInterface;
import com.bluslee.mundo.process.test.reflection.AbstractSubClass2;
import com.bluslee.mundo.process.test.reflection.HavaParamConstructorSubClass;
import com.bluslee.mundo.process.utils.ReflectionTools;
import com.bluslee.mundo.xml.XmlConfiguration;
import com.bluslee.mundo.xml.XmlParserImpl;
import com.bluslee.mundo.xml.base.BaseXmlParser;
import com.bluslee.mundo.xml.base.XmlParser;
import org.junit.Assert;
import org.junit.Test;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

/**
 * ReflectionToolsTest.
 * @author carl.che
 */
public class ReflectionToolsTest {

    @Test
    public void subTypesTest() {
        String packName = "com.bluslee.mundo.process.test";
        Set<Class> expectClasses = new HashSet<>();
        expectClasses.add(NormalSubClass.class);
        expectClasses.add(AbstractSubClass.class);
        expectClasses.add(HavaParamConstructorSubClass.class);
        Set<Class<? extends SuperInterface>> actualClasses = ReflectionTools.instance().subTypes(packName, SuperInterface.class);
        Assert.assertEquals(expectClasses, actualClasses);
        //test Reflections cache
        Set<Class> expectClassesCache = new HashSet<>();
        expectClassesCache.add(NormalSubClass.class);
        expectClassesCache.add(AbstractSubClass.class);
        expectClassesCache.add(HavaParamConstructorSubClass.class);
        Set<Class<? extends SuperInterface>> actualClassesCache = ReflectionTools.instance().subTypes(packName, SuperInterface.class);
        Assert.assertEquals(actualClassesCache, actualClassesCache);
    }

    @Test
    public void subTypeByConfigTest() {
        Configuration configuration = new XmlConfiguration();
        Set<Class<? extends XmlParser>> actualClasses = ReflectionTools.instance().subTypes(configuration, XmlParser.class);
        Set<Class> expectClasses = new HashSet<>();
        expectClasses.add(BaseXmlParser.class);
        expectClasses.add(XmlParserImpl.class);
        Assert.assertEquals(expectClasses, actualClasses);
        Configuration testConfiguration = new TestConfiguration();
        Set<Class<? extends XmlParser>> testActualClasses = ReflectionTools.instance().subTypes(testConfiguration, XmlParser.class);
        Assert.assertNull(testActualClasses);
    }

    @Test
    public void firstNoParamConstructorSubInstanceByConfigTest() {
        Configuration configuration = new XmlConfiguration();
        XmlParser xmlParser = ReflectionTools.instance().firstNoParamConstructorSubInstance(configuration, XmlParser.class);
        Assert.assertTrue(xmlParser instanceof XmlParserImpl);
        Configuration testConfiguration = new TestConfiguration();
        Set<Class<? extends XmlParser>> testActualClasses = ReflectionTools.instance().subTypes(testConfiguration, XmlParser.class);
        Assert.assertNull(testActualClasses);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest() {
        String packName = "com.bluslee.mundo.process.test";
        SuperInterface superInterface = ReflectionTools.instance().firstNoParamConstructorSubInstance(packName, SuperInterface.class);
        Assert.assertTrue(superInterface instanceof NormalSubClass);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest2() {
        TestConfiguration testConfiguration = new TestConfiguration();
        SuperInterface superInterface = ReflectionTools
                .instance(Collections.emptyMap())
                .firstNoParamConstructorSubInstance(testConfiguration, SuperInterface.class);
        Assert.assertNull(superInterface);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest3() {
        TestConfiguration testConfiguration = new TestConfiguration();
        testConfiguration.setProperty(Constants.ConfigKey.MODE_PACKAGE_NAME, "com.bluslee.mundo.process.test");
        SuperInterface superInterface = ReflectionTools
                .instance(Collections.emptyMap())
                .firstNoParamConstructorSubInstance(testConfiguration, SuperInterface.class);
        Assert.assertTrue(superInterface instanceof NormalSubClass);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest4() {
        String packName = "com.bluslee.mundo.process.test";
        NormalSubClass normalSubClass = ReflectionTools.instance().firstNoParamConstructorSubInstance(packName, NormalSubClass.class);
        Assert.assertNull(normalSubClass);
    }

    @Test
    public void firstNoParamConstructorSubInstanceTest5() {
        String packName = "com.bluslee.mundo.process.test";
        AbstractSubClass2 subInstance = ReflectionTools.instance().firstNoParamConstructorSubInstance(packName, AbstractSubClass2.class);
        Assert.assertNull(subInstance);
    }

    private static class TestConfiguration implements Configuration {

        private final Map<String, String> map = new HashMap<>();

        @Override
        public Configuration setProperty(final String key, final String value) {
            map.put(key, value);
            return this;
        }

        @Override
        public Object getProperty(final String key) {
            return map.get(key);
        }

        @Override
        public String getMode() {
            return null;
        }

        @Override
        public void load(final InputStream inStream) {

        }

        @Override
        public void init() {

        }

        @Override
        public byte[] getInitData() {
            return new byte[0];
        }
    }
}
