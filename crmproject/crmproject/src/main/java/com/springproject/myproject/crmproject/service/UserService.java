package com.springproject.myproject.crmproject.service;

import com.springproject.myproject.crmproject.model.User;
import com.springproject.myproject.crmproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Map<String, Object>> getUserCountByRole() {
        List<Object[]> counts = userRepository.getUserCountByRole();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : counts) {
            Map<String, Object> map = new HashMap<>();
            map.put("role", row[0]);
            map.put("count", row[1]);
            result.add(map);
        }

        return result;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}