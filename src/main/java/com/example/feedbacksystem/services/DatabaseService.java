package com.example.feedbacksystem.services;

import java.sql.*;
import java.io.*;
import javax.swing.JOptionPane;

public class DatabaseService {
    private static final String URL = "jdbc:postgresql://localhost:5433/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "arsen";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addFeedback(String name, String email, String message) {
        String sql = "INSERT INTO feedback(name, email, message, deleted) VALUES(?,?,?,false)";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, message);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateFeedback(int id, String message) {
        String sql = "UPDATE feedback SET message=? WHERE id=?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, message);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void softDelete(int id) {
        String sql = "UPDATE feedback SET deleted=true WHERE id=?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void restoreFeedback(int id) {
        String sql = "UPDATE feedback SET deleted=false WHERE id=?";
        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static ResultSet getActive() throws Exception {
        return connect().createStatement().executeQuery("SELECT * FROM feedback WHERE deleted=false ORDER BY id DESC");
    }

    public static ResultSet getDeleted() throws Exception {
        return connect().createStatement().executeQuery("SELECT * FROM feedback WHERE deleted=true ORDER BY id DESC");
    }

    public static void exportToCSV(String path) {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM feedback");
             PrintWriter writer = new PrintWriter(new File(path))) {

            writer.println("ID,Name,Email,Message,Deleted");
            while (rs.next()) {
                writer.println(rs.getInt("id") + "," + rs.getString("name") + "," +
                        rs.getString("email") + "," + rs.getString("message") + "," +
                        rs.getBoolean("deleted"));
            }
            JOptionPane.showMessageDialog(null, "Экспорт завершен: " + path);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void importFromCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // Пропускаем заголовок
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                if (d.length >= 4) addFeedback(d[1], d[2], d[3]);
            }
            JOptionPane.showMessageDialog(null, "Импорт завершен!");
        } catch (Exception e) { e.printStackTrace(); }
    }
}