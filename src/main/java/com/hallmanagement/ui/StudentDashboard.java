package com.hallmanagement.ui;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private JButton btnMealSelection;
    private JButton btnMealHistory;
    private JButton btnStudentBill;
    private JButton btnLogout;
    private JLabel lblTitle;
    private JPanel mainPanel;

    public StudentDashboard() {
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel();
        lblTitle = new JLabel();
        btnMealSelection = new JButton();
        btnMealHistory = new JButton();
        btnStudentBill = new JButton();
        btnLogout = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Student Dashboard - Hall Management System");
        setResizable(false);

        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setText("Student Dashboard");

        btnMealSelection.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnMealSelection.setText("1. Meal Selection");
        btnMealSelection.addActionListener(evt -> btnMealSelectionActionPerformed());

        btnMealHistory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnMealHistory.setText("2. Meal History");
        btnMealHistory.addActionListener(evt -> btnMealHistoryActionPerformed());

        btnStudentBill.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnStudentBill.setText("3. View Bills");
        btnStudentBill.addActionListener(evt -> btnStudentBillActionPerformed());

        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setText("Logout");
        btnLogout.addActionListener(evt -> btnLogoutActionPerformed());

        // Layout setup
        mainPanel.setLayout(new GridLayout(3, 1, 10, 15));
        mainPanel.add(btnMealSelection);
        mainPanel.add(btnMealHistory);
        mainPanel.add(btnStudentBill);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(lblTitle);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnLogout);

        getContentPane().setLayout(new BorderLayout(10, 20));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    private void btnMealSelectionActionPerformed() {
        openFrameSafely("com.hallmanagement.ui.MealSelectionFrame");
    }

    private void btnMealHistoryActionPerformed() {
        openFrameSafely("com.hallmanagement.ui.MealHistoryFrame");
    }

    private void btnStudentBillActionPerformed() {
        openFrameSafely("com.hallmanagement.ui.StudentBillFrame");
    }

    private void btnLogoutActionPerformed() {
        this.dispose();
        new LoginFrame().setVisible(true);
    }

    private void openFrameSafely(String className) {
        try {
            Class<?> cls = Class.forName(className);
            JFrame frame = (JFrame) cls.getDeclaredConstructor().newInstance();
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Frame open kora jacche na: " + className + "\nError: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}