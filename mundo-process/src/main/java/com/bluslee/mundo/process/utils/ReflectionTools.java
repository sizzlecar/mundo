package com.bluslee.mundo.process.utils;

import com.bluslee.mundo.core.configuration.Configuration;
import com.bluslee.mundo.core.exception.MundoException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ReflectionUtils.
 * @author carl.che
 * @date 2021/11/25
 */
public final class ReflectionTools {

    private static volatile ReflectionTools reflectionTools;

    private final Cache<String, Reflections> reflectionsCache = CacheBuilder
            .newBuilder()
            .maximumSize(10)
            .build();

    private final Map<String, String> defaultModePackageMap = new HashMap<>();

    private ReflectionTools() {
        defaultModePackageMap.put("xml", "com.bluslee.mundo.xml");
    }

    public static ReflectionTools instance() {
        if (reflectionTools == null) {
            synchronized (ReflectionTools.class) {
                if (reflectionTools == null) {
                    reflectionTools = new ReflectionTools();
                }
            }
        }
        return reflectionTools;
    }

    /**
     * 寻找指定包下指定类型的的所有子类.
     * @param packageName 包名
     * @param clazz 指定类型的Class对象
     * @param <T> 指定类型
     * @return 指定包下指定类型的的所有子类
     */
    public <T> Set<Class<? extends T>> subTypes(final String packageName, final Class<T> clazz) {
        Reflections reflections = reflectionsCache.getIfPresent(packageName);
        if (reflections == null) {
            reflections = new Reflections(packageName);
            reflectionsCache.put(packageName, reflections);
        }
        return reflections.getSubTypesOf(clazz);
    }

    /**
     * 根据配置mode(先从defaultModePackageMap中找，找不到就从配置找key为mode.package的配置)，找出对应的包名，然后找出该包下，指定类型的所有的子类.
     * @param configuration 配置
     * @param clazz 指定类型的Class对象
     * @param <T> 指定类型
     * @return 指定包下指定类型的的所有子类
     */
    public <T> Set<Class<? extends T>> subTypes(final Configuration configuration, final Class<T> clazz) {
        String defaultPackageName = defaultModePackageMap.get(configuration.getMode());
        if (defaultPackageName == null) {
            Object configPackage = configuration.getProperty("mode.package");
            if (configPackage == null) {
                return null;
            }
            defaultPackageName = configPackage.toString();
        }
        return subTypes(defaultPackageName, clazz);
    }

    /**
     * 找出指定包下指定类型的所有的子类中第一个不是抽象类的且有无参的构造方法的子类的实例.
     * @param packageName 报名
     * @param clazz 指定类型的Class对象
     * @param <T> 指定类型
     * @return 指定包下指定类型的所有的子类中不是抽象类的且有无参的构造方法的子类的实例
     */
    public <T> T firstNoParamConstructorSubInstance(final String packageName, final Class<T> clazz) {
        Set<Class<? extends T>> subTypes = subTypes(packageName, clazz);
        if (subTypes == null || subTypes.size() == 0) {
            return null;
        }
        AtomicReference<Constructor<?>> noParamConstructor = new AtomicReference<>();
        Optional<Class<? extends T>> optionalClass = subTypes.stream()
                .filter(cla -> !Modifier.isAbstract(cla.getModifiers()))
                .filter(cla -> {
                    Constructor<?>[] constructors = cla.getConstructors();
                    for (Constructor<?> constructor : constructors) {
                        int parameterCount = constructor.getParameterCount();
                        if (parameterCount == 0) {
                            noParamConstructor.set(constructor);
                            return true;
                        }
                    }
                    return false;
                }).findFirst();
        if (!optionalClass.isPresent()) {
            return null;
        }
        Constructor<?> constructor = noParamConstructor.get();
        Object instance = null;
        try {
            instance = constructor.newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new MundoException("create instance error", e);
        }
        return (T) instance;
    }

    /**
     * 根据配置mode(先从defaultModePackageMap中找，找不到就从配置找key为mode.package的配置)，找出对应的包名，
     * 然后找出该包下，指定包下指定类型的所有的子类中第一个不是抽象类的且有无参的构造方法的子类的实例.
     * @param configuration 配置
     * @param clazz 指定类型的Class对象
     * @param <T> 指定类型
     * @return 指定包下指定类型的所有的子类中不是抽象类的且有无参的构造方法的子类的实例
     */
    public <T> T firstNoParamConstructorSubInstance(final Configuration configuration, final Class<T> clazz) {
        String defaultPackageName = defaultModePackageMap.get(configuration.getMode());
        if (defaultPackageName == null) {
            Object configPackage = configuration.getProperty("mode.package");
            if (configPackage == null) {
                return null;
            }
            defaultPackageName = configPackage.toString();
        }
        return firstNoParamConstructorSubInstance(defaultPackageName, clazz);
    }
}
