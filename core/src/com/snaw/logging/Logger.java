package com.snaw.logging;

import java.io.PrintStream;

public class Logger {
    private static LogLevel minLogLevel = LogLevel.MAX_LOG;
    private static PrintStream logOut = System.out;

    public static void log(String string, LogLevel logLevel) {
        if (logLevel != LogLevel.NO_LOG && logLevel.ordinal() >= minLogLevel.ordinal()) {
            log(string);
        }
    }

    public static void log(Exception exception, LogLevel logLevel) {
        if (logLevel != LogLevel.NO_LOG && logLevel.ordinal() >= minLogLevel.ordinal()) {
            log(exception);
        }
    }

    private static void log(String string) {
        logOut.println(string);
    }

    private static void log(Exception exception) {
        exception.printStackTrace(logOut);
    }
}
