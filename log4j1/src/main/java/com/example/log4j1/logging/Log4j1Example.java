package com.example.log4j1.logging;

import org.apache.log4j.Logger;

public class Log4j1Example {
    private static final Logger logger = Logger.getLogger(Log4j1Example.class);

    public void Log() {
        logger.info("Log4j1: This is an info message from Log4j1");
    }
}
