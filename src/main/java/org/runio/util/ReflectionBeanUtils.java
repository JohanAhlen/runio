package org.runio.util;

import java.lang.reflect.Field;

public class ReflectionBeanUtils {

    private ReflectionBeanUtils() {
    }

    public static void setField(String fieldName, Object targetBean, Object value) {
        try {
            Class<?> clazz = targetBean.getClass();
            Field field = clazz.getDeclaredField(fieldName);
            boolean previousAccessibleValue = field.isAccessible();
            field.setAccessible(true);
            field.set(targetBean, value);
            field.setAccessible(previousAccessibleValue);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Could not set field '%s' on target '%s' with value of type '%s'", fieldName, targetBean.getClass(), value.getClass()), e);
        }
    }
}
