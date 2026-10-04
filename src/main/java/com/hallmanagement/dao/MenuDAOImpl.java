package com.hallmanagement.dao;

import com.hallmanagement.config.DBConnection;
import java.sql.*;

public class MenuDAOImpl {

    // Get price of a meal type (Breakfast, Lunch, Dinner)
    public static double getMealPrice(String mealType) {
        double price = 0.0;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT price FROM meal_prices WHERE meal_type = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, mealType);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                price = rs.getDouble("price"); // match column name here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price;
    }

    // Update menu for a specific day (Saturday–Friday)
    public boolean updateDayMenu(String day, String breakfast, String lunch, String dinner) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE weekly_menu SET breakfast=?, lunch=?, dinner=? WHERE day=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, breakfast);
            ps.setString(2, lunch);
            ps.setString(3, dinner);
            ps.setString(4, day);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
