package com.example.feedbacksystem;

import com.example.feedbacksystem.models.Admin;
import com.example.feedbacksystem.models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI {

    public static String role = "";

    public static void show() {
        JDialog dialog = new JDialog((Frame) null, "Sign In", true);
        dialog.setSize(320, 280);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.getContentPane().setBackground(new Color(236, 236, 236));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JTextField userField = new JTextField();
        userField.setPreferredSize(new Dimension(200, 30));
        JPasswordField passField = new JPasswordField();
        passField.setPreferredSize(new Dimension(200, 30));

        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 1;
        panel.add(userField, gbc);
        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 3;
        panel.add(passField, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttons.setOpaque(false);
        JButton cancelBtn = new JButton("Cancel");
        JButton loginBtn = new JButton("Login");

        // Стиль кнопки macOS
        loginBtn.setBackground(new Color(0, 122, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);

        buttons.add(cancelBtn);
        buttons.add(loginBtn);
        gbc.gridy = 4;
        panel.add(buttons, gbc);

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            // ДЕМОНСТРАЦИЯ ПОЛИМОРФИЗМА (Пункт №10 задания)
            User currentUser;

            if (u.equals("admin") && p.equals("123")) {
                role = "ADMIN";
                currentUser = new Admin(u, p); // Объект подкласса
                currentUser.showMenu();        // Вызовет "Admin menu"
                dialog.dispose();
            } else if (u.equals("user") && p.equals("123")) {
                role = "USER";
                currentUser = new User(u, p, "USER"); // Объект суперкласса
                currentUser.showMenu();               // Вызовет "User menu"
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> System.exit(0));

        dialog.add(panel);
        dialog.setVisible(true);
    }
}