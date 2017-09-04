package com.randioo.majiang_collections_client.utils;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;

public class UIUtils {
    @SuppressWarnings("unchecked")
    public static <T> T get(Object target, String name) {
        Field field = ReflectionUtils.findField(target.getClass(), name);
        ReflectionUtils.makeAccessible(field);
        return (T) ReflectionUtils.getField(field, target);
    }
}
