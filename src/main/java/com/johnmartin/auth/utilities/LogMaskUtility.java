package com.johnmartin.auth.utilities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.auth.annotations.Sensitive;

public class LogMaskUtility {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Object mask(Object obj) {
        if (obj == null)
            return null;

        // primitives / strings → return as is
        if (isPrimitiveOrWrapper(obj.getClass()) || obj instanceof String) {
            return obj;
        }

        try {
            Map<String, Object> map = mapper.convertValue(obj, Map.class);
            Map<String, Object> masked = new HashMap<>(map);

            // handle fields (normal classes)
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Sensitive.class)) {
                    masked.put(field.getName(), "[PROTECTED]");
                }
            }

            return masked;

        } catch (Exception e) {
            return obj.toString();
        }
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() || type == Integer.class || type == Long.class || type == Double.class
               || type == Float.class || type == Boolean.class || type == Byte.class || type == Short.class
               || type == Character.class;
    }
}
