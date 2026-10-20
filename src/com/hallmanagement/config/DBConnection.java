/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.hallmanagement.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection { // 'public' থাকা আবশ্যক
    private static final String URL = "jdbc:mysql://localhost:3306/hall_meal_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // আপনার আসল MySQL পাসওয়ার্ড দিন
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() { // 'public static' থাকা আবশ্যক
        if (connection == null) {
            try {
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