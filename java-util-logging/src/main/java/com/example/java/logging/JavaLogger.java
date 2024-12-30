package com.example.java.logging;

import java.util.logging.Logger;

public class JavaLogger {
    private static final Logger logger = Logger.getLogger(JavaLogger.class.getName());

    public void Log() {

        logger.info("JUL: This is an info message from Java Util Logging");
    }
}
