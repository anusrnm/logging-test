package com.example.test;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HelloServletTest {

    private HelloServlet testServlet;
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    @Before
    public void setUp() throws Exception {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Initialize the servlet
        testServlet = new HelloServlet();

        // Mock response's PrintWriter
        when(response.getWriter()).thenReturn(writer);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        // Act: Call the servlet's doGet method
        testServlet.doGet(request, response);

        // Assert: Verify that the servlet writes the expected output
        verify(response).setContentType("text/html");
        verify(writer).println("<h1>Hello, World!</h1>");
    }
}