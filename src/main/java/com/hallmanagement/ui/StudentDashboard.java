package com.hallmanagement.ui;

import com.hallmanagement.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {
    private Student currentStudent;

    public StudentDashboard() {
        this(null);
    }

    public StudentDashboard(Student student) {
        this.currentStudent = student;
        
        // Window Configuration
        setTitle("Student Dashboard - " + (student != null ? student.getName() : "Student"));
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Screen-er majkhane open hobe
        setLayout(new BorderLayout(10, 10));

        // Welcome Header Label
        JLabel lblWelcome = new JLabel("Welcome, " + (student != null ? student.getName() : "Student") + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 18));
        lblWelcome.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblWelcome, BorderLayout.NORTH);

        // Center Panel for Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // Buttons Initialization
        JButton btnSelectMeal = new JButton("Select Meal");
        JButton btnTodayMenu = new JButton("View Today Menu");
        JButton btnHistory = new JButton("Meal History");
        JButton btnBill = new JButton("Monthly Bill");
        JButton btnProfile = new JButton("My Profile");
        JButton btnLogout = new JButton("Logout");

        // Styling Buttons
        JButton[] buttons = {btnSelectMeal, btnTodayMenu, btnHistory, btnBill, btnProfile, btnLogout};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Arial", Font.PLAIN, 14));
            btn.setFocusable(false);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Action Listeners for Buttons
        
        // 1. Select Meal
        btnSelectMeal.addActionListener(e -> {
            try {
                // Member 3-er MealSelectionFrame connect hobe
                Class<?> cls = Class.forName("com.hallmanagement.ui.MealSelectionFrame");
                JFrame frame = (JFrame) cls.getConstructor(Student.class).newInstance(currentStudent);
                frame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Meal Selection Frame is being created by Member 3.");
            }
        });

        // 2. View Today Menu
        btnTodayMenu.addActionListener(e -> {
            try {
                // Member 2-er TodayMenuFrame connect hobe
                Class<?> cls = Class.forName("com.hallmanagement.ui.TodayMenuFrame");
                JFrame frame = (JFrame) cls.getConstructor().newInstance();
                frame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Today Menu Frame is being created by Member 2.");
            }
        });

        // 3. Meal History
        btnHistory.addActionListener(e -> {
            try {
                // Member 3-er MealHistoryFrame connect hobe
                Class<?> cls = Class.forName("com.hallmanagement.ui.MealHistoryFrame");
                JFrame frame = (JFrame) cls.getConstructor(Student.class).newInstance(currentStudent);
                frame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Meal History Frame is being created by Member 3.");
            }
        });

        // 4. Monthly Bill
        btnBill.addActionListener(e -> {
            try {
                // Member 3-er StudentBillFrame connect hobe
                Class<?> cls = Class.forName("com.hallmanagement.ui.StudentBillFrame");
                JFrame frame = (JFrame) cls.getConstructor(Student.class).newInstance(currentStudent);
                frame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Student Bill Frame is being created by Member 3.");
            }
        });

        // 5. My Profile
        btnProfile.addActionListener(e -> {
            try {
                Class<?> cls = Class.forName("com.hallmanagement.ui.StudentProfileFrame");
                JFrame frame = (JFrame) cls.getConstructor(Student.class).newInstance(currentStudent);
                frame.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please create StudentProfileFrame in com.hallmanagement.ui package.");
            }
        });

        // 6. Logout
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                "Are you sure you want to logout?", 
                "Logout Confirmation", 
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); // Close current dashboard
                try {
                    Class<?> cls = Class.forName("com.hallmanagement.ui.LoginFrame");
                    JFrame frame = (JFrame) cls.getConstructor().newInstance();
                    frame.setVisible(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Opening Login Window...");
                }
            }
        });
    }
    public static void main(String[] args) {
    com.hallmanagement.model.Student testStudent = new com.hallmanagement.model.Student();
    testStudent.setId(1); // Apnar DB-er kono valid student id
    testStudent.setName("Rahim");
    testStudent.setPhone("01700000000");
    testStudent.setPassword("123456");

    java.awt.EventQueue.invokeLater(() -> {
        new StudentDashboard(testStudent).setVisible(true);
    });
}
}