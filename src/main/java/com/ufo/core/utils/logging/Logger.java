package com.ufo.core.utils.logging;

/**
 * Created by FOG on 12.02.2018.
 * <p>
 * Static class as alternative to create new Logger instance in each class you want to log
 * Class can be also adapted for TestNG reporter logging
 */
public class Logger {

    private final static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger("com.ufo");

    public static void fatal(final String message) {
        LOGGER.fatal(message);
    }

    public static void fatal(final String message, final Throwable t) {
        LOGGER.fatal(message, t);
    }

    public static void error(final String message) {
        LOGGER.error(message);
    }

    public static void error(final String message, final Throwable t) {
        LOGGER.error(message, t);
    }

    public static void debug(final String message, final Throwable t) {
        LOGGER.debug(message, t);
    }

    public static void warn(final String message, final Throwable t) {
        LOGGER.warn(message, t);
    }

    public static void info(final String message, final Throwable t) {
        LOGGER.info(message, t);
    }

    public static void info(final String message) {
        LOGGER.info(message);
    }

    public static void warn(final String message) {
        LOGGER.warn(message);
    }

    public static void debug(final String message) {
        LOGGER.debug(message);
    }

}
