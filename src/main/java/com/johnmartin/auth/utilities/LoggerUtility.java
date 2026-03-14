package com.johnmartin.auth.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoggerUtility {

    private LoggerUtility() {
        // prevent instantiation
    }

    public static Logger getLogger(String tag) {
        return LoggerFactory.getLogger(tag);
    }

    public static void i(String tag, String message, Object... args) {
        getLogger(tag).info(message, args);
    }

    public static void d(String tag, String message, Object... args) {
        getLogger(tag).debug(message, args);
    }

    public static void w(String tag, String message, Object... args) {
        getLogger(tag).warn(message, args);
    }

    public static void e(String tag, String message, Throwable throwable) {
        getLogger(tag).error(message, throwable);
    }

    public static void t(String tag, String message, Object... args) {
        getLogger(tag).trace(message, args);
    }
}
