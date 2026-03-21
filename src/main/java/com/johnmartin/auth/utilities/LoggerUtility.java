package com.johnmartin.auth.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtility {

    private LoggerUtility() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    private static Object[] maskArgs(Object... args) {
        if (args == null)
            return null;

        Object[] masked = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            masked[i] = LogMaskUtility.mask(args[i]);
        }

        return masked;
    }

    public static void i(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).info(message, maskArgs(args));
    }

    public static void d(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).debug(message, maskArgs(args));
    }

    public static void w(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).warn(message, maskArgs(args));
    }

    public static void e(Class<?> clazz, String message, Throwable throwable) {
        getLogger(clazz).error(message, throwable);
    }

    public static void t(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).trace(message, maskArgs(args));
    }
}
