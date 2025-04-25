
package com.springproject.myproject.crmproject.controller;
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

