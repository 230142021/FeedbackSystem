package com.example.feedbacksystem.services;

import com.example.feedbacksystem.models.User;
import java.util.*;

public class AuthService {

    private List<User> users = new ArrayList<>();

    public AuthService() {
        // Инициализация пользователей
        users.add(new User("admin", "123", "ADMIN"));
        users.add(new User("user", "123", "USER"));
    }

    public User login(String username, String password) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null); // Возвращает пользователя или null, если не найден
    }
}