package com.example.feedbacksystem.services;

import com.example.feedbacksystem.models.Feedback;
import java.util.*;

public class FeedbackService {

    private List<Feedback> list = new ArrayList<>();
    // idCounter больше не нужен, так как ID генерирует база данных (Serial)

    // CREATE
    public void addFeedback(String name, String email, String message) {
        // Отправляем в базу
        DatabaseService.addFeedback(name, email, message);
        System.out.println("✅ Feedback saved to DB!");
    }

    // READ (Синхронизация с базой)
    public List<Feedback> getAllFromDB() {
        list.clear();
        try (java.sql.ResultSet rs = DatabaseService.getActive()) {
            while (rs.next()) {
                list.add(new Feedback(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("message"),
                        rs.getBoolean("deleted") // Учитываем новое поле
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE
    public void updateFeedback(int id, String newMessage) {
        DatabaseService.updateFeedback(id, newMessage);
    }

    // DELETE (Soft Delete)
    public void deleteFeedback(int id) {
        DatabaseService.softDelete(id);
    }
}