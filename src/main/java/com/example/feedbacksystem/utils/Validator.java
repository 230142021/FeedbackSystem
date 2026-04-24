package com.example.feedbacksystem.utils;

public class Validator {

    // Более строгая проверка email через регулярное выражение
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }

    public static boolean isNotEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    // Проверка на минимальную длину сообщения
    public static boolean isValidMessage(String msg) {
        return isNotEmpty(msg) && msg.trim().length() >= 3;
    }
}