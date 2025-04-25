package com.springproject.myproject.crmproject.dto;

import com.springproject.myproject.crmproject.model.Role;
import lombok.Getter;

@Getter
public class AuthResponse {
    private final String token;
    private final String username;
    private final Role role;

    public AuthResponse(String token, String username, Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }
}
