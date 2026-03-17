package com.johnmartin.auth.security;

import java.lang.reflect.Field;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnmartin.auth.annotations.Sensitive;

public class LogMaskUtil {

    public static String mask(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = mapper.convertValue(obj, Map.class);

            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Sensitive.class)) {
                    map.put(field.getName(), "[PROTECTED]");
                }
            }

            return mapper.writeValueAsString(map);

        } catch (Exception e) {
            return obj.toString();
        }
    }
}
