package com.example.logback.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackExample {
    private static final Logger logger = LoggerFactory.getLogger(LogbackExample.class);

    public void Log() {
        logger.info("Logback: This is an info message from Logback");
    }
}
