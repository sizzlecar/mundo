package com.bluslee.mundo.springboot.starter.test;

import com.bluslee.mundo.core.exception.MundoException;
import com.bluslee.mundo.springboot.starter.MundoProperties;
import com.google.common.base.CaseFormat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

/**
 * 读取配置mundo.enabled == true的配置文件单元测试.
 * @author carl.che
 */
@ActiveProfiles(value = "enabled-true")
public class MundoPropertiesEnabledTrueTest extends MundoStarterTest {

    @Autowired(required = false)
    private MundoProperties actualProperties;

    @Test
    public void parsePropertiesTest() {
        //mundo.enabled == true, MundoProperties不能为空
        Assertions.assertNotNull(actualProperties);
        //使用Yaml直接读取配置文件比较配置文件与注入的属性是否一致
        Yaml yaml = new Yaml();
        InputStream resourceAsStream = getClass().getResourceAsStream("/application-enabled-true.yml");
        Map<String, Object> yamlMap = yaml.load(resourceAsStream);
        Optional<Map> mundoMapOpt = Optional.ofNullable(yamlMap)
                .flatMap(map -> Optional.ofNullable(map.get("mundo")))
                .flatMap(mundoObj -> Optional.ofNullable((Map) mundoObj));
        Assertions.assertTrue(mundoMapOpt.isPresent());
        propertyEquals(mundoMapOpt.get(), actualProperties);
    }

    private void propertyEquals(final Map<String, Object> map, final Object instance) {
        map.forEach((key, val) -> {
            String getterMethodName = null;
            //统一命名风格
            if (key.contains("-")) {
                getterMethodName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, String.format("get-%s", key));
            } else if (key.contains("_")) {
                getterMethodName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, String.format("get_%s", key));
            } else {
                getterMethodName = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, String.format("get-%s", key));
            }
            Method declaredMethod = null;
            try {
                declaredMethod = instance.getClass().getDeclaredMethod(getterMethodName, null);
            } catch (NoSuchMethodException e) {
                throw new MundoException("get method error", e);
            }
            Assertions.assertNotNull(declaredMethod);
            Object invokeRes = null;
            try {
                invokeRes = declaredMethod.invoke(instance, null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new MundoException("invoke method error", e);
            }
            if (val instanceof Map) {
                propertyEquals((Map) val, invokeRes);
            } else {
                Assertions.assertEquals(val, invokeRes);
            }
        });
    }

}
