package com.metarash.backend.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ObjectUtils {

    /**
     * @param target to
     * @param source from
     */
    public static <T> T copyNonNullProperties(T target, T source, String... ignoreProperties) {
        if (target == null || source == null) {
            throw new IllegalArgumentException("Neither target or source can be null");
        }
        Set<String> ignoreSet = new HashSet<>(Arrays.asList(ignoreProperties));
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (ignoreSet.contains(field.getName())) {
                continue;
            }
            field.setAccessible(true);
            try {
                Object value = field.get(source);
                if (value != null) {
                    field.set(target, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return target;
    }
}
