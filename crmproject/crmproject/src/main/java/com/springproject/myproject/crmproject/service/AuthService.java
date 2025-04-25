//package com.springproject.myproject.crmproject.service;
//
//import com.springproject.myproject.crmproject.ResponseDTO.UserResponse;
//import com.springproject.myproject.crmproject.dto.AuthRequest;
//import com.springproject.myproject.crmproject.model.Role;
//import com.springproject.myproject.crmproject.model.User;
//import com.springproject.myproject.crmproject.repository.UserRepository;
//import com.springproject.myproject.crmproject.security.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public Optional<UserResponse> register(AuthRequest authRequest) {
//        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
//            return Optional.empty(); // Username already taken
//        }
//
//        User user = new User();
//        user.setUsername(authRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
//        user.setEmail(authRequest.getEmail());
//
//        try {
//            user.setRole(Role.valueOf(authRequest.getRole().toUpperCase()));
//        } catch (IllegalArgumentException e) {
//            throw new IllegalArgumentException("Invalid role. Allowed values: CUSTOMER, ADMIN, SUPERADMIN.");
//        }
//
//        User savedUser = userRepository.save(user);
//
//        return Optional.of(new UserResponse(
//                savedUser.getId(),
//                savedUser.getUsername(),
//                savedUser.getRole(),
//                savedUser.getEmail(),
//                "User registered successfully!"
//        ));
//    }
//
//    public String login(Map<String, String> request) {
//        User user = userRepository.findByUsername(request.get("username"))
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (passwordEncoder.matches(request.get("password"), user.getPassword())) {
//            return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//}
package com.springproject.myproject.crmproject.service;

import com.springproject.myproject.crmproject.ResponseDTO.UserResponse;
import com.springproject.myproject.crmproject.dto.AuthRequest;
import com.springproject.myproject.crmproject.exception.InvalidCredentialsException;
import com.springproject.myproject.crmproject.model.Role;
import com.springproject.myproject.crmproject.model.User;
import com.springproject.myproject.crmproject.repository.UserRepository;
import com.springproject.myproject.crmproject.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public Optional<UserResponse> register(AuthRequest authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            return Optional.empty();
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getEmail());

        try {
            user.setRole(Role.valueOf(authRequest.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role. Allowed values: CUSTOMER, ADMIN, SUPERADMIN.");
        }

        User savedUser = userRepository.save(user);

        return Optional.of(new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole(),
                savedUser.getEmail(),
                "User registered successfully!"
        ));
    }

    public String login(Map<String, String> request) {
        User user = userRepository.findByUsername(request.get("username"))
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        if (passwordEncoder.matches(request.get("password"), user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        } else {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
