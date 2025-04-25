package com.springproject.myproject.crmproject.ResponseDTO;

import com.springproject.myproject.crmproject.model.Role;

public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private String email;
    private String message;

    public UserResponse(Long id, String username, Role role, String email, String message) {
        this.id = id;
        this.username = username;
        this.role = role.name();
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
