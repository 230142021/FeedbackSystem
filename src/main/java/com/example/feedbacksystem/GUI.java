package com.example.feedbacksystem;

import com.example.feedbacksystem.services.DatabaseService;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;

public class GUI {
    static JTable table;
    static DefaultTableModel model;
    static JFrame frame;

    public static void main(String[] args) {
        setupTheme();
        startApp();
    }

    public static void startApp() {
        // 1. Показываем логин
        LoginGUI.show();

        // 2. ФИКС: Если роль не выбрана (окно закрыли), выходим
        if (LoginGUI.role == null || LoginGUI.role.isEmpty()) {
            System.exit(0);
        }

        frame = new JFrame("Feedback System PRO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 800);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(242, 242, 247));

        // Главный контейнер
        JPanel root = new JPanel(new BorderLayout(20, 20));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(20, 25, 20, 25));

        // --- ШАПКА ---
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JLabel title = new JLabel("Feedback Control Center");
        title.setFont(new Font(".AppleSystemUIFont", Font.BOLD, 24));

        JButton logoutBtn = createMacBtn("Logout ⎋", new Color(142, 142, 147));
        logoutBtn.setPreferredSize(new Dimension(100, 35));
        logoutBtn.addActionListener(e -> {
            frame.dispose(); // Закрываем основное окно
            LoginGUI.role = ""; // Сбрасываем роль
            startApp(); // Запускаем заново
        });
        header.add(title, BorderLayout.WEST);
        header.add(logoutBtn, BorderLayout.EAST);

        // --- КАРТОЧКА ВВОДА ---
        JPanel inputCard = new JPanel(new GridBagLayout());
        inputCard.setBackground(Color.WHITE);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 225), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameF = createStyledField();
        JTextField emailF = createStyledField();
        JTextField msgF = createStyledField();

        addInputRow(inputCard, "👤 Name", nameF, gbc, 0);
        addInputRow(inputCard, "📧 Email", emailF, gbc, 1);
        addInputRow(inputCard, "💬 Message", msgF, gbc, 2);

        // Кнопки управления
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        actions.setOpaque(false);
        JButton addBtn = createMacBtn("Add Record", new Color(0, 122, 255));
        JButton updateBtn = createMacBtn("Save Changes", new Color(88, 86, 214));
        actions.add(updateBtn); actions.add(addBtn);

        // Сборка верхней части (ФИКС ОШИБКИ LAYOUT)
        JPanel topWrapper = new JPanel(new BorderLayout(0, 15));
        topWrapper.setOpaque(false);
        topWrapper.add(header, BorderLayout.NORTH);
        topWrapper.add(inputCard, BorderLayout.CENTER);
        topWrapper.add(actions, BorderLayout.SOUTH);

        // --- ТАБЛИЦА ---
        model = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Message"}, 0);
        table = new JTable(model);
        table.setRowHeight(35);

        // 🔥 АВТОЗАПОЛНЕНИЕ ПРИ ВЫБОРЕ
        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) {
                nameF.setText(model.getValueAt(r, 1).toString());
                emailF.setText(model.getValueAt(r, 2).toString());
                msgF.setText(model.getValueAt(r, 3).toString());
            }
        });
        JScrollPane scroll = new JScrollPane(table);

        // --- НИЖНЯЯ ПАНЕЛЬ ---
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottom.setOpaque(false);
        JButton delBtn = createMacBtn("Delete", new Color(255, 59, 48));
        JButton trashBtn = createMacBtn("Trash Bin", new Color(142, 142, 147));
        JButton refBtn = createMacBtn("Refresh", new Color(52, 199, 89));
        JButton expBtn = createMacBtn("Export CSV", Color.BLACK);
        JButton impBtn = createMacBtn("Import CSV", Color.BLACK);
        bottom.add(delBtn); bottom.add(trashBtn); bottom.add(refBtn); bottom.add(expBtn); bottom.add(impBtn);

        // Сборка в root
        root.add(topWrapper, BorderLayout.NORTH);
        root.add(scroll, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        // Логика кнопок
        addBtn.addActionListener(e -> { DatabaseService.addFeedback(nameF.getText(), emailF.getText(), msgF.getText()); load(); });
        updateBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) { DatabaseService.updateFeedback((int)model.getValueAt(r,0), msgF.getText()); load(); }
        });
        delBtn.addActionListener(e -> {
            int r = table.getSelectedRow();
            if (r != -1) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Move to Trash?", "Admin Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) { DatabaseService.softDelete((int)model.getValueAt(r,0)); load(); }
            }
        });
        refBtn.addActionListener(e -> load());
        trashBtn.addActionListener(e -> TrashWindow.show(() -> load()));
        expBtn.addActionListener(e -> DatabaseService.exportToCSV("data.csv"));
        impBtn.addActionListener(e -> { DatabaseService.importFromCSV("data.csv"); load(); });

        if (!LoginGUI.role.equals("ADMIN")) delBtn.setEnabled(false);

        frame.add(root);
        load();
        frame.setVisible(true);
    }

    private static void addInputRow(JPanel p, String label, JTextField f, GridBagConstraints gbc, int y) {
        gbc.gridy = y; gbc.gridx = 0; gbc.weightx = 0.1;
        p.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.weightx = 0.9;
        p.add(f, gbc);
    }

    private static JTextField createStyledField() {
        JTextField f = new JTextField();
        f.setPreferredSize(new Dimension(200, 40));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 215), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return f;
    }

    private static JButton createMacBtn(String t, Color bg) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(150, 45));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    private static void setupTheme() {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
    }

    static void load() {
        model.setRowCount(0);
        try (ResultSet rs = DatabaseService.getActive()) {
            while (rs.next()) model.addRow(new Object[]{rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("message")});
        } catch (Exception e) {}
    }
}