package com.bluslee.mundo.process.utils;

import com.bluslee.mundo.core.configuration.Configuration;
import org.reflections.Reflections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ReflectionUtils.
 * @author carl.che
 * @date 2021/11/25
 */
public final class ReflectionUtils {

    private static volatile ReflectionUtils reflectionUtils;

    private Map<String, String> modePackageMap = new HashMap<>();

    private ReflectionUtils() {
        //update todo
        modePackageMap.put("xml", "com.bluslee.mundo.xml");
    }

    public static ReflectionUtils instance() {
        if (reflectionUtils == null) {
            synchronized (ReflectionUtils.class) {
                if (reflectionUtils == null) {
                    reflectionUtils = new ReflectionUtils();
                }
            }
        }
        return reflectionUtils;
    }

    public <T> Set<Class<? extends T>> subTypes(final String packageName, final Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz);
    }

    public <T> Set<Class<? extends T>> subTypes(final Configuration configuration, final Class<T> clazz) {
        Reflections reflections = new Reflections(modePackageMap.get(configuration.getMode()));
        return reflections.getSubTypesOf(clazz);
    }
}
