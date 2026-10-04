/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hallmanagement.ui;

import com.hallmanagement.dao.StudentDAOImpl;
import com.hallmanagement.model.Student;

import javax.swing.*;
import java.awt.*;

public class StudentProfileFrame extends JFrame {
    private Student currentStudent;
    private JTextField txtName;
    private JTextField txtPhone;
    private JPasswordField txtPassword;
    private JButton btnSave;
    private JButton btnCancel;

    public StudentProfileFrame(Student student) {
        this.currentStudent = student;

        // Window Setup
        setTitle("Update Profile - " + (student != null ? student.getName() : ""));
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Screen-er majkhane open hobe
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JLabel lblHeader = new JLabel("Edit Profile Details", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblHeader, BorderLayout.NORTH);

        // Form Fields Panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField(student != null ? student.getName() : "");
        formPanel.add(txtName);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField(student != null ? student.getPhone() : "");
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField(student != null ? student.getPassword() : "");
        formPanel.add(txtPassword);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnSave = new JButton("Save Changes");
        btnCancel = new JButton("Cancel");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Save Button Logic
        btnSave.addActionListener(e -> {
            String updatedName = txtName.getText().trim();
            String updatedPhone = txtPhone.getText().trim();
            String updatedPassword = new String(txtPassword.getPassword()).trim();

            if (updatedName.isEmpty() || updatedPhone.isEmpty() || updatedPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            StudentDAOImpl dao = new StudentDAOImpl();
            boolean success = dao.updateProfile(currentStudent.getId(), updatedName, updatedPhone, updatedPassword);

            if (success) {
                // Local object memory state update
                currentStudent.setName(updatedName);
                currentStudent.setPhone(updatedPhone);
                currentStudent.setPassword(updatedPassword);

                JOptionPane.showMessageDialog(this, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Close current frame
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update profile. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Cancel Button Action
        btnCancel.addActionListener(e -> this.dispose());
    }
}