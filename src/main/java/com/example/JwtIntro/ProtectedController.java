package com.example.JwtIntro;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {

    @GetMapping("/open")
    public String open() {
        return "Detta är en öppen endpoint";
    }

    @GetMapping("/secured")
    public String secured(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        return "Denna endpoint är säker dig: " + username;
    }
}
