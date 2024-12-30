package com.example.slf4j.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jExample {
    private static final Logger logger = LoggerFactory.getLogger(Slf4jExample.class);

    public void Log() {
        logger.info("SLF4J: This is an info message from SLF4J");
    }
}
