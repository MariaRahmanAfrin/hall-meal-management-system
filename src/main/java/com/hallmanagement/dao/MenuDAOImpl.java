package com.hallmanagement.dao;

import com.hallmanagement.config.DBConnection;
import com.hallmanagement.model.MealPrice;
import com.hallmanagement.model.MenuItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAOImpl {

    public List<MenuItem> getWeeklyMenu() {
        List<MenuItem> menuList = new ArrayList<>();
        String query = "SELECT day_name, breakfast_item, lunch_item, dinner_item FROM weekly_menu";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                menuList.add(new MenuItem(
                    rs.getString("day_name"),
                    rs.getString("breakfast_item"),
                    rs.getString("lunch_item"),
                    rs.getString("dinner_item")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }

    public boolean updateDayMenu(String dayName, String breakfast, String lunch, String dinner) {
        String query = "UPDATE weekly_menu SET breakfast_item = ?, lunch_item = ?, dinner_item = ? WHERE day_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, breakfast);
            pstmt.setString(2, lunch);
            pstmt.setString(3, dinner);
            pstmt.setString(4, dayName);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public MealPrice getMealPrices() {
        String query = "SELECT breakfast_price, lunch_price, dinner_price FROM meal_prices WHERE id = 1";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return new MealPrice(
                    rs.getDouble("breakfast_price"),
                    rs.getDouble("lunch_price"),
                    rs.getDouble("dinner_price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new MealPrice(0.0, 0.0, 0.0);
    }
    public boolean updateMealPrices(double breakfast, double lunch, double dinner) {
    String query = "UPDATE meal_prices SET breakfast_price = ?, lunch_price = ?, dinner_price = ? WHERE id = 1";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setDouble(1, breakfast);
        pstmt.setDouble(2, lunch);
        pstmt.setDouble(3, dinner);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public static void main(String[] args) {
        MenuDAOImpl dao = new MenuDAOImpl();
        
        // 1. Fetch Weekly Menu Test
        System.out.println("--- Fetching Weekly Menu ---");
        for (MenuItem item : dao.getWeeklyMenu()) {
            System.out.println(item.getDayName() + ": " + item.getBreakfastItem() + ", " + item.getLunchItem() + ", " + item.getDinnerItem());
        }
        
        // 2. Fetch Meal Prices Test
        MealPrice price = dao.getMealPrices();
        System.out.println("--- Fetching Meal Prices ---");
        System.out.println("Breakfast: " + price.getBreakfastPrice() + ", Lunch: " + price.getLunchPrice() + ", Dinner: " + price.getDinnerPrice());
    }

}