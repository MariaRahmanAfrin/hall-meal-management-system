package com.hallmanagement.ui;

import com.hallmanagement.config.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentRegisterFrame extends JFrame {

    private JTextField txtName, txtEmail, txtPhone;
    private JPasswordField txtPassword;
    private JButton btnRegister, btnBack;

    public StudentRegisterFrame() {
        setTitle("Student Registration - Hall Management System");
        setSize(420, 380);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lblHeader = new JLabel("REGISTER STUDENT", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 20));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(lblHeader, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnRegister = new JButton("Register");
        btnBack = new JButton("Back to Login");

        btnPanel.add(btnRegister);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        btnRegister.addActionListener(e -> registerStudent());
        btnBack.addActionListener(e -> {
            this.dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void registerStudent() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            // Check Duplicate Email
            String checkQuery = "SELECT id FROM users WHERE LOWER(email) = LOWER(?)";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Email already registered!", "Duplicate Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Insert New Student
            String insertQuery = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, 'STUDENT')";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.setString(4, password);
                pstmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Registration Successful! Please Login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new LoginFrame().setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for direct testing/Shift + F6 execution
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRegisterFrame().setVisible(true));
    }
}