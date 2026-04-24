package com.example.feedbacksystem.services;

import com.example.feedbacksystem.models.Feedback;
import java.io.*;
import java.util.*;
import java.nio.file.*;

public class FileService {

    private static final String FILE_PATH = "data/feedback.txt";

    // SAVE
    public static void save(List<Feedback> list) {
        try {
            // Создаем папку data, если её нет
            Files.createDirectories(Paths.get("data"));

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Feedback f : list) {
                    // Добавляем сохранение статуса deleted (f.isDeleted())
                    writer.write(f.getId() + ";" + f.getName() + ";" + f.getEmail() + ";" + f.getMessage() + ";" + f.isDeleted());
                    writer.newLine();
                }
                System.out.println("✅ Saved to file!");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    // LOAD
    public static List<Feedback> load() {
        List<Feedback> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Используем ";" вместо ",", так как в сообщениях часто бывают запятые
                String[] p = line.split(";");
                if (p.length >= 5) {
                    list.add(new Feedback(
                            Integer.parseInt(p[0]), // id
                            p[1],                  // name
                            p[2],                  // email
                            p[3],                  // message
                            Boolean.parseBoolean(p[4]) // deleted status
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading file.");
        }
        return list;
    }
}