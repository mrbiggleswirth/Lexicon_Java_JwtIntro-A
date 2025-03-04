package com.example.JwtIntro;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        // Authorization : "Bearer kq3j4ytidjkngbliq34tyha.ekrgnhkldrhg"

        String username = null;

        if ( header != null && header.startsWith("Bearer ")) {
             String jwt = header.substring(7);
             username = jwtUtil.validateToken(jwt);
        }

        if (username != null) {
            // token är giltig
            request.setAttribute("username", username);
        } else if (!request.getRequestURI().startsWith("/auth")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
