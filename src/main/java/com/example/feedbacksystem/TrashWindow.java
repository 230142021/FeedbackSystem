package com.example.feedbacksystem;

import com.example.feedbacksystem.services.DatabaseService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class TrashWindow {
    public static void show(Runnable onAction) {
        JFrame frame = new JFrame("🗑 Trash Bin");
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Email", "Message"}, 0);
        JTable table = new JTable(model);

        try (ResultSet rs = DatabaseService.getDeleted()) {
            while (rs.next()) model.addRow(new Object[]{rs.getInt("id"), rs.getString("email"), rs.getString("message")});
        } catch (Exception e) {}

        JButton restoreBtn = new JButton("Restore Selected Feedback");
        restoreBtn.setBackground(new Color(88, 86, 214));
        restoreBtn.setForeground(Color.WHITE);

        restoreBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                DatabaseService.restoreFeedback((int)model.getValueAt(row, 0));
                model.removeRow(row);
                onAction.run();
                JOptionPane.showMessageDialog(frame, "Feedback restored to active list!");
            }
        });

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(restoreBtn, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}