package com.hallmanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hall_meal_db";
    private static final String USER = "root"; // আপনার MySQL username
    private static final String PASSWORD = "maRia#123"; // আপনার MySQL password
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // MySQL Connector 8.x/9.x এর জন্য আধুনিক ড্রাইভ লোডিং
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    
}