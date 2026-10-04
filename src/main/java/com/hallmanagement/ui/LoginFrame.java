package com.hallmanagement.ui;

import com.hallmanagement.dao.StudentDAOImpl;
import com.hallmanagement.model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRole;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Hall Meal Management System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // স্ক্রিনের মাঝে দেখাবে
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("LOGIN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // Role Selection
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Role:"), gbc);

        comboRole = new JComboBox<>(new String[]{"Student", "Admin"});
        gbc.gridx = 1; gbc.gridy = 1;
        add(comboRole, gbc);

        // Username / Roll / Email
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Username/Email:"), gbc);

        txtUsername = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtUsername, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Password:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        add(txtPassword, gbc);

        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(52, 152, 219));
        btnLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(btnLogin, gbc);

        // Button Action Event
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String role = (String) comboRole.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Admin".equals(role)) {
            if ("admin".equals(username) && "admin123".equals(password)) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                this.dispose();
                // Admin Dashboard call hobe
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // ডাটাবেজ দিয়ে স্টুডেন্ট অথেন্টিকেশন
            StudentDAOImpl studentDAO = new StudentDAOImpl();
            Student student = studentDAO.authenticateUser(username, password);

            if (student != null) {
                JOptionPane.showMessageDialog(this, "Welcome " + student.getName() + "! Student Login Successful.");
                
                // LoginFrame বন্ধ হবে
                this.dispose(); 
                
                // ডাটাবেজের সেই আসল Student object পাঠিয়া Dashboard ওপেন হবে
                new StudentDashboard(student).setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Student Email or Password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}