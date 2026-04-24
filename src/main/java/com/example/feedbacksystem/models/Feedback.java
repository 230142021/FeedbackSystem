package com.example.feedbacksystem.models;

public class Feedback {

    private int id;
    private String name;
    private String email;
    private String message;
    private boolean deleted; // Новое поле для синхронизации с БД

    // Конструктор
    public Feedback(int id, String name, String email, String message, boolean deleted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
        this.deleted = deleted;
    }

    // Геттеры
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
    public boolean isDeleted() { return deleted; }

    // Сеттеры
    public void setMessage(String message) { this.message = message; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
}