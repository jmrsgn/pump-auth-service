package com.johnmartin.auth.utilities;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LogMaskUtility {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String mask(Object obj) {
        if (obj == null)
            return "null";

        Class<?> clazz = obj.getClass();
        StringBuilder result = new StringBuilder(clazz.getSimpleName() + "[");

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                if (field.isAnnotationPresent(com.johnmartin.auth.annotations.Sensitive.class)) {
                    value = "***";
                }

                result.append(field.getName()).append("=").append(value);

            } catch (IllegalAccessException e) {
                result.append(field.getName()).append("=ERROR");
            }

            if (i < fields.length - 1) {
                result.append(", ");
            }
        }

        result.append("]");
        return result.toString();
    }
}
