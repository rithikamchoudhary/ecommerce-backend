package com.ecommerce.backend.auth;

import com.ecommerce.backend.model.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.service.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody AuthRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return "Username already exists!";
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUsername(), encodedPassword, "USER"); // default role = USER
        userRepository.save(user);

        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return userRepository.findByUsername(request.getUsername())
                // .filter(user -> passwordEncoder.matches(request.getPassword(),
                // user.getPassword()))
                // .map(user -> jwtService.generateToken(user.getUsername()))
                // .orElseThrow(() -> new RuntimeException("Invalid username or password"));
                .map(user -> {
                    System.out.println("🔍 Username found: " + user.getUsername());
                    System.out.println("🔐 Stored hash: " + user.getPassword());
                    System.out.println("🔐 Raw entered: " + request.getPassword());
                    boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
                    System.out.println("✅ Password match? " + matches);

                    if (!matches)
                        throw new RuntimeException("Password mismatch");
                    return jwtService.generateToken(user.getUsername());
                })
                .orElseThrow(() -> new RuntimeException("Username not found"));
    }

}
