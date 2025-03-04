package com.example.JwtIntro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> user) {
        String username = user.get("username");
        String password = user.get("password");

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isPresent() && passwordEncoder.matches(password, optionalUser.get().getPassword()) ){

            String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(Collections.singletonMap("token", token));

        } else {
            return ResponseEntity.status(401).body("Invalid username or passowrd");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader ){

        if(authHeader == null || !authHeader.startsWith("Bearer ") ){
            return ResponseEntity.status(401).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        String username = jwtUtil.validateToken(token);

        if(username != null ){
            return ResponseEntity.ok(Collections.singletonMap("user", username));
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}

