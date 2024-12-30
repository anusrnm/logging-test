package com.example.test;

import java.io.IOException;

import com.example.commons.logging.CommonsLogger;
import com.example.java.logging.JavaLogger;
import com.example.log4j1.logging.Log4j1Example;
import com.example.logback.logging.LogbackExample;
import com.example.slf4j.logging.Slf4jExample;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        CommonsLogger logger = new CommonsLogger();
        logger.Log();
        JavaLogger logger2 = new JavaLogger();
        logger2.Log();
        Log4j1Example log4j1Example = new Log4j1Example();
        log4j1Example.Log();
        LogbackExample logbackExample = new LogbackExample();
        logbackExample.Log();
        Slf4jExample slf4jExample = new Slf4jExample();
        slf4jExample.Log();
        response.getWriter().println("<h1>Hello, World!</h1>");
    }
}
