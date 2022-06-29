package org.thejava.assignment.util;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("jwtFilter")
public class JwtFilter extends OncePerRequestFilter {


    private String jwt = "";
    private String requestUri = "";

    @Autowired
    JwtTokenUtil jwtUtil;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       // TODO authorization mechanism can be done here


        filterChain.doFilter(request, response);


    }

    private boolean isExcluded() {
        if (requestUri.contains("/fontawesome") || requestUri.contains("api/unsecured") || requestUri.contains("/static") ||
                requestUri.contains("/img") || requestUri.equals("/favicon.ico") || requestUri.equals("/")) {
            return true;
        } else {
            return false;
        }
    }
}
