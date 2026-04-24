package com.example.feedbacksystem.models;

import com.example.feedbacksystem.services.DatabaseService;

public class Admin extends User {

    // Добавляем конструктор для корректного создания админа
    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

    @Override
    public void showMenu() {
        System.out.println("--- Welcome, Admin: " + getUsername() + " ---");
    }

    // Метод теперь делает реальное удаление через сервис
    public void deleteFeedback(int feedbackId) {
        DatabaseService.softDelete(feedbackId);
        System.out.println("Admin " + getUsername() + " deleted feedback ID: " + feedbackId);
    }
}