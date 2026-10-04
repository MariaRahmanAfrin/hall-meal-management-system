package com.hallmanagement.ui;

import com.hallmanagement.config.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginFrame extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;

    public LoginFrame() {
        setTitle("Hall Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JLabel lblHeader = new JLabel("LOGIN", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblHeader, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail = new JTextField();

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword = new JPasswordField();

        formPanel.add(lblEmail);
        formPanel.add(txtEmail);
        formPanel.add(lblPassword);
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");

        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 12));

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);

        add(buttonPanel, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> handleLogin());
        btnRegister.addActionListener(e -> {
            this.dispose();
            openFrameSafely("com.hallmanagement.ui.StudentRegisterFrame");
        });
    }

    private void handleLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Email and Password!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String query = "SELECT * FROM users WHERE LOWER(email) = LOWER(?) AND password = ?";

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Failed to connect to Database!", "DB Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, email);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String role = rs.getString("role");

                        JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();

                        // Role check kore respective Dashboard call:
                        if (role != null && role.trim().equalsIgnoreCase("ADMIN")) {
                            new AdminDashboard().setVisible(true); // Member 2-er Admin Dashboard
                        } else {
                            new StudentDashboard().setVisible(true); // Student Dashboard
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Email or Password!", "Login Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
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
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}