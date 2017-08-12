package com.iostark.x10.base.logging;

import com.iostark.x10.base.annotation.api.API_Beta;
import com.iostark.x10.base.annotation.visibility.KeepEntryPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Logging registry.
 *
 * @since V0_5_0
 */
@API_Beta
@KeepEntryPoint
public final class LoggingRegistry {
    private static Appender defaultAppender;
    private static Appender currentAppender;
    private static Map<String, Logger> loggers = new HashMap<>();

    static {
        defaultAppender = new AndroidAppender();
    }

    private LoggingRegistry() {
    }

    /**
     * Set default logging appender.
     *
     * @param appender Appender
     * @since V0_5_0
     */
    @KeepEntryPoint
    public static void setDefaultAppender(final Appender appender) {
        if (appender == null) {
            currentAppender = new NullAppender();
        } else {
            currentAppender = appender;
        }
    }

    /**
     * Get logger by name and create if not existing.
     *
     * @param moduleName Logger name
     * @return Logger instance
     * @since V0_5_0
     */
    public static synchronized Logger getLoggerOrCreate(final String moduleName) {
        Logger logger = loggers.get(moduleName);
        if (logger == null) {
            logger = new LoggerImpl(moduleName, currentAppender != null ? currentAppender : defaultAppender);
            loggers.put(moduleName, logger);
        }
        return logger;
    }
}
