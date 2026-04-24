package com.example.feedbacksystem;

import com.example.feedbacksystem.models.Admin;
import com.example.feedbacksystem.models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI {
    public static String role = "";

    public static void show() {
        role = ""; // Сброс роли при открытии

        JDialog dialog = new JDialog((Frame) null, "Sign In", true);
        dialog.getContentPane().setBackground(new Color(236, 236, 236));
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Главная панель с GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // Поля ввода с указанием ширины (columns)
        JTextField userField = new JTextField(15);
        JPasswordField passField = new JPasswordField(15);

        // Добавляем компоненты на панель
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridy = 1;
        panel.add(userField, gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridy = 3;
        panel.add(passField, gbc);

        // Панель кнопок
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttons.setOpaque(false);

        JButton cancelBtn = new JButton("Cancel");
        JButton loginBtn = new JButton("Login");

        // Стиль основной кнопки (синяя Mac-style)
        loginBtn.setBackground(new Color(0, 122, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 13));

        buttons.add(cancelBtn);
        buttons.add(loginBtn);

        gbc.gridy = 4;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel.add(buttons, gbc);

        // Логика кнопок
        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            User currentUser;
            if (u.equals("admin") && p.equals("123")) {
                role = "ADMIN";
                currentUser = new Admin(u, p);
                currentUser.showMenu(); // Демонстрация полиморфизма в консоли
                dialog.dispose();
            } else if (u.equals("user") && p.equals("123")) {
                role = "USER";
                currentUser = new User(u, p, "USER");
                currentUser.showMenu();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> System.exit(0));

        // Добавляем панель в диалог
        dialog.add(panel);

        // ВАЖНО: Фикс пустой отрисовки
        dialog.pack();                   // Устанавливает размер под компоненты
        dialog.setLocationRelativeTo(null); // Центрирует
        dialog.setResizable(false);
        dialog.setVisible(true);         // Показывает только после сборки
    }
}