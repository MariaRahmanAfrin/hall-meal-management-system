package com.hallmanagement.dao;

import com.hallmanagement.config.DBConnection;
import com.hallmanagement.model.MealSelection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealSelectionDAOImpl {

    // Save a new meal selection (skip if duplicate)
    public boolean saveSelection(MealSelection meal) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT IGNORE INTO meal_selections(student_id, selection_date, breakfast, lunch, dinner) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, meal.getStudentId());
            ps.setDate(2, meal.getSelectionDate());
            ps.setBoolean(3, meal.isBreakfast());
            ps.setBoolean(4, meal.isLunch());
            ps.setBoolean(5, meal.isDinner());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Fetch all meals for a student
    public List<MealSelection> getSelectionsByStudent(int studentId) {
        List<MealSelection> meals = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM meal_selections WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MealSelection meal = new MealSelection(
                    rs.getInt("student_id"),
                    rs.getDate("selection_date"),
                    rs.getBoolean("breakfast"),
                    rs.getBoolean("lunch"),
                    rs.getBoolean("dinner")
                );
                meal.setId(rs.getInt("id"));
                meals.add(meal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
