package com.springproject.myproject.crmproject.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Not found user");
    }
}
