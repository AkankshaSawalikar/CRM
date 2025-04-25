//
//package com.springproject.myproject.crmproject.controller;
//
//import com.springproject.myproject.crmproject.ResponseDTO.UserResponse;
//import com.springproject.myproject.crmproject.dto.AuthRequest;
//import com.springproject.myproject.crmproject.exception.InvalidCredentialsException;
//import com.springproject.myproject.crmproject.exception.UserAlreadyExistsException;
//import com.springproject.myproject.crmproject.model.User;
//import com.springproject.myproject.crmproject.repository.UserRepository;
//import com.springproject.myproject.crmproject.security.JwtUtil;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//        import com.springproject.myproject.crmproject.model.Role;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtUtil jwtUtil;
//
//    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
//        // Throw custom exception if user exists
//        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
//            throw new UserAlreadyExistsException("Username is already taken!");
//        }
//
//        // Convert role from String to Enum with validation
//        Role role;
//        try {
//            role = Role.valueOf(authRequest.getRole().toUpperCase());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body("Invalid role. Allowed values: CUSTOMER, ADMIN, SUPERADMIN.");
//        }
//
//        // Create and save user
//        User user = new User();
//        user.setUsername(authRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
//        user.setEmail(authRequest.getEmail());
//        user.setRole(role);
//
//        User savedUser = userRepository.save(user);
//
//        return ResponseEntity.ok(new UserResponse(
//                savedUser.getId(),
//                savedUser.getUsername(),
//                savedUser.getRole(),
//                savedUser.getEmail(),
//                "User registered successfully!"
//        ));
//    }
//
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> request) {
//        User user = userRepository.findByUsername(request.get("username"))
//                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
//
//        if (passwordEncoder.matches(request.get("password"), user.getPassword())) {
//            return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
//        } else {
//            throw new InvalidCredentialsException("Invalid credentials");
//        }
//    }
//}
package com.springproject.myproject.crmproject.controller;

import com.springproject.myproject.crmproject.ResponseDTO.UserResponse;
import com.springproject.myproject.crmproject.dto.AuthRequest;
import com.springproject.myproject.crmproject.exception.InvalidCredentialsException;
import com.springproject.myproject.crmproject.exception.UserAlreadyExistsException;
import com.springproject.myproject.crmproject.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        try {
            return authService.register(authRequest)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new UserAlreadyExistsException("Username is already taken!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String token = authService.login(request);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

