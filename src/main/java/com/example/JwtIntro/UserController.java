package com.example.JwtIntro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody Map<String, String> userData){
        String username = userData.get("username");
        String password = userData.get("password");
        String role = userData.get("role");

        if(userRepository.findByUsername(username).isPresent()){
            return ResponseEntity.badRequest().body("User already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, encodedPassword, Role.valueOf(role));
        userRepository.save(newUser);

        return ResponseEntity.ok("User created successfully!");
    }
}
