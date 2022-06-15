package com.gilang.common.util;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ClassUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gylang
 * data 2021/2/20
 */
public class ClassUtils extends ClassUtil {


    /**
     * 递归
     *
     * @param clazz 当前被代理类
     * @return (被代理)原类型
     */
    public static Class<?> getUserClass(Class<?> clazz) {

        if (clazz.getName().contains("$$")) {
            Class<?> superclass = clazz.getSuperclass();
            Class<?>[] interfaces = clazz.getInterfaces();
            if (superclass != null && superclass != Object.class) {
                // 被代理类通过接口方式实现, 继承实现, 如cglib
                return getUserClass(superclass);

            } else if (ArrayUtil.isNotEmpty(interfaces)) {
                // 被代理类通过接口方式实现, jdk动态代理
                for (Class<?> anInterface : interfaces) {
                    if (anInterface != null && anInterface != Object.class) {
                        return getUserClass(anInterface);
                    }
                }
            }
        }

        return clazz;
    }

    /**
     * 递归获取所有的父类接口
     *
     * @param clazz 被解析的类
     * @return 所有的父类
     */
    public static List<Class<?>> resolveAllType(Class<?> clazz) {

        List<Class<?>> types = new ArrayList<>();

        doResolveAllType(types, clazz);
        return types;
    }

    /**
     * 递归获取所有的父类接口
     *
     * @param clazz 被解析的类
     */
    private static void doResolveAllType(List<Class<?>> types, Class<?> clazz) {
        if (clazz != Object.class) {
            types.add(clazz);
            if (clazz.getName().contains("$$")) {
                Class<?> superclass = clazz.getSuperclass();
                Class<?>[] interfaces = clazz.getInterfaces();
                if (superclass != null) {
                    // 被代理类通过接口方式实现, 继承实现, 如cglib
                    doResolveAllType(types, superclass);

                } else if (ArrayUtil.isNotEmpty(interfaces)) {
                    // 被代理类通过接口方式实现, jdk动态代理
                    for (Class<?> anInterface : interfaces) {
                        if (anInterface != null) {
                            doResolveAllType(types, anInterface);
                        }
                    }
                }
            }
        }
    }


}
