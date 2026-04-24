package com.example.feedbacksystem;

import com.example.feedbacksystem.models.Admin;
import com.example.feedbacksystem.models.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginGUI {
    public static String role = "";

    public static void show() {
        role = ""; // Сбрасываем при каждом вызове
        JDialog dialog = new JDialog((Frame) null, "Sign In", true);
        dialog.setSize(320, 300);
        dialog.setLocationRelativeTo(null);
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

        gbc.gridy = 0; panel.add(new JLabel("Username:"), gbc);
        gbc.gridy = 1; panel.add(userField, gbc);
        gbc.gridy = 2; panel.add(new JLabel("Password:"), gbc);
        gbc.gridy = 3; panel.add(passField, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.setOpaque(false);
        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(0, 122, 255));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setOpaque(true);
        loginBtn.setBorderPainted(false);

        buttons.add(loginBtn);
        gbc.gridy = 4; panel.add(buttons, gbc);

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            User currentUser; // Полиморфная переменная

            if (u.equals("admin") && p.equals("123")) {
                role = "ADMIN";
                currentUser = new Admin(u, p);
                currentUser.showMenu(); // Вызов переопределенного метода
                dialog.dispose();
            } else if (u.equals("user") && p.equals("123")) {
                role = "USER";
                currentUser = new User(u, p, "USER");
                currentUser.showMenu();
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Access Denied");
            }
        });

        dialog.setVisible(true);
    }
}