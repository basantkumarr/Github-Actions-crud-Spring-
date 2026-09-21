package com.example.crud.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;


 public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        // Convert generic request/response into HTTP request/response
        HttpServletRequest httpServletRequest =
                (HttpServletRequest) servletRequest;

        HttpServletResponse httpServletResponse =
                (HttpServletResponse) servletResponse;



String requestId= UUID.randomUUID().toString();
   httpServletResponse.setHeader("X-reqID",requestId);
        filterChain.doFilter(servletRequest, servletResponse);

         /*
        // Before controller
        System.out.println("===== Request In =====");
        System.out.println("Method : " + httpServletRequest.getMethod());
        System.out.println("URL    : " + httpServletRequest.getRequestURI());

        // Continue request → DispatcherServlet → Controller
        filterChain.doFilter(servletRequest, servletResponse);

        // After controller
        System.out.println("===== Request Out =====");
        System.out.println("Status : " + httpServletResponse.getStatus()); */
    }
}