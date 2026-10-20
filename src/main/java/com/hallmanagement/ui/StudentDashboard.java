package com.hallmanagement.ui;

import com.hallmanagement.model.Student;
import javax.swing.*;
import java.awt.*;

public class StudentDashboard extends JFrame {

    private Student currentStudent;

    // Default constructor for direct testing/GUI builder compatibility
    public StudentDashboard() {
        this(null);
    }

    // Dynamic constructor accepting authenticated Student object from LoginFrame
    public StudentDashboard(Student student) {
        this.currentStudent = student;
        initComponents();
    }

    private void initComponents() {
        setTitle("Student Dashboard - Hall Management System");
        setSize(480, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        // Member 1 Task: Dynamic Greeting Header
        String studentName = (currentStudent != null && currentStudent.getName() != null) 
                             ? currentStudent.getName() : "Student";
        JLabel lblHeader = new JLabel("Welcome, " + studentName + "!", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setForeground(new Color(0, 102, 204));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblHeader, BorderLayout.NORTH);

        // SOW Day 3 Member 1 & Member 3 Required Feature Buttons
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 12));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JButton btnSelectMeal = new JButton("Select Meal");
        JButton btnViewTodayMenu = new JButton("View Today Menu");
        JButton btnMealHistory = new JButton("Meal History");
        JButton btnMonthlyBill = new JButton("Monthly Bill");
        JButton btnMyProfile = new JButton("My Profile");
        JButton btnLogout = new JButton("Logout");

        // UI Styling
        Font btnFont = new Font("Arial", Font.PLAIN, 14);
        btnSelectMeal.setFont(btnFont);
        btnViewTodayMenu.setFont(btnFont);
        btnMealHistory.setFont(btnFont);
        btnMonthlyBill.setFont(btnFont);
        btnMyProfile.setFont(btnFont);
        btnLogout.setFont(btnFont);

        menuPanel.add(btnSelectMeal);
        menuPanel.add(btnViewTodayMenu);
        menuPanel.add(btnMealHistory);
        menuPanel.add(btnMonthlyBill);
        menuPanel.add(btnMyProfile);
        menuPanel.add(btnLogout);

        add(menuPanel, BorderLayout.CENTER);

        // Navigation Handlers using openFrameSafely for runtime stability
        btnSelectMeal.addActionListener(e -> openFrameSafely("com.hallmanagement.ui.MealSelectionFrame"));
        btnViewTodayMenu.addActionListener(e -> openFrameSafely("com.hallmanagement.ui.TodayMenuFrame"));
        btnMealHistory.addActionListener(e -> openFrameSafely("com.hallmanagement.ui.MealHistoryFrame"));
        btnMonthlyBill.addActionListener(e -> openFrameSafely("com.hallmanagement.ui.StudentBillFrame"));
        btnMyProfile.addActionListener(e -> openFrameSafely("com.hallmanagement.ui.StudentProfileFrame"));

        // Member 1 Task: Session Clear & Logout to LoginFrame
        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    // Reflection-based safe launcher to prevent compilation failure if sub-frames are still under development
    private void openFrameSafely(String className) {
        try {
            Class<?> cls = Class.forName(className);
            JFrame frame;
            
            // Pass current logged-in student if constructor expects Student object
            try {
                frame = (JFrame) cls.getDeclaredConstructor(Student.class).newInstance(currentStudent);
            } catch (NoSuchMethodException e) {
                frame = (JFrame) cls.getDeclaredConstructor().newInstance();
            }
            
            frame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Feature Frame open kora jacche na: " + className + "\nError: " + ex.getMessage(),
                "Navigation Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}