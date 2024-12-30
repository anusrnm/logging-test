package com.example.commons.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonsLogger {
    private static final Log logger = LogFactory.getLog(CommonsLogger.class);

    public void Log() {
        logger.info("Commons Logging: This is an info message from Commons Logging");
    }
}
