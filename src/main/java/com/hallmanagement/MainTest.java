package com.hallmanagement;

import com.hallmanagement.config.DBConnection;
import com.hallmanagement.dao.StudentDAOImpl;
import com.hallmanagement.model.Student;

public class MainTest {
    public static void main(String[] args) {
        // 1. Verify DB Connection
        System.out.println("--- Testing DB Connection ---");
        DBConnection.getConnection();

        // 2. Test Student Registration with a unique email each run
        System.out.println("\n--- Testing Student Save ---");
        StudentDAOImpl studentDAO = new StudentDAOImpl();
        String uniqueEmail = "test" + System.currentTimeMillis() + "@example.com";
        Student newStudent = new Student(0, "Test Student", uniqueEmail, "pass123", "01700000000");
        
        boolean saved = studentDAO.save(newStudent);
        System.out.println("Student Saved Successfully: " + saved);

        // 3. Test Student Authentication with the newly saved user
        System.out.println("\n--- Testing Student Authentication ---");
        Student loggedInStudent = studentDAO.authenticateUser(uniqueEmail, "pass123");
        if (loggedInStudent != null) {
            System.out.println("Login Success! Authenticated User: " + loggedInStudent.getName());
        } else {
            System.out.println("Login Failed!");
        }
    }
}