package com.hallmanagement.dao;

import com.hallmanagement.config.DBConnection;
import com.hallmanagement.model.MealSelection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MealSelectionDAOImpl {

    // ✅ Save a new meal selection (skip if duplicate)
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

    // ✅ Fetch all meals for a student
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

    // ✅ Count how many times a student took a specific meal
    public int getMealCountByStudent(String studentId, String mealType) {
        int count = 0;
        String column;

        switch (mealType.toLowerCase()) {
            case "breakfast": column = "breakfast"; break;
            case "lunch": column = "lunch"; break;
            case "dinner": column = "dinner"; break;
            default: return 0; // invalid meal type
        }

        String sql = "SELECT COUNT(*) FROM meal_selections " +
                     "WHERE student_id = ? AND " + column + " = TRUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // ✅ NEW: Fetch student’s date‑wise meal history (for MealHistoryFrame)
    public List<MealSelection> getMealHistoryByStudent(String studentId) {
        List<MealSelection> meals = new ArrayList<>();
        String sql = "SELECT * FROM meal_selections WHERE student_id = ? ORDER BY selection_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
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

    // ✅ NEW: Count meals for current month (for StudentBillFrame)
    public int getMonthlyMealCountByStudent(String studentId, String mealType, int month, int year) {
        int count = 0;
        String column;

        switch (mealType.toLowerCase()) {
            case "breakfast": column = "breakfast"; break;
            case "lunch": column = "lunch"; break;
            case "dinner": column = "dinner"; break;
            default: return 0;
        }

        String sql = "SELECT COUNT(*) FROM meal_selections " +
                     "WHERE student_id = ? AND " + column + " = TRUE " +
                     "AND MONTH(selection_date) = ? AND YEAR(selection_date) = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studentId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // ✅ NEW: Monthly summary report (for AdminReportFrame)
    public String getMonthlySummary(int month, int year) {
        StringBuilder report = new StringBuilder();

        String sql = "SELECT " +
                     "SUM(CASE WHEN breakfast = TRUE THEN 1 ELSE 0 END) AS total_breakfasts, " +
                     "SUM(CASE WHEN lunch = TRUE THEN 1 ELSE 0 END) AS total_lunches, " +
                     "SUM(CASE WHEN dinner = TRUE THEN 1 ELSE 0 END) AS total_dinners " +
                     "FROM meal_selections WHERE MONTH(selection_date) = ? AND YEAR(selection_date) = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int breakfasts = rs.getInt("total_breakfasts");
                int lunches = rs.getInt("total_lunches");
                int dinners = rs.getInt("total_dinners");

                // Prices from MenuDAOImpl
                double breakfastPrice = com.hallmanagement.dao.MenuDAOImpl.getMealPrice("Breakfast");
                double lunchPrice = com.hallmanagement.dao.MenuDAOImpl.getMealPrice("Lunch");
                double dinnerPrice = com.hallmanagement.dao.MenuDAOImpl.getMealPrice("Dinner");

                double totalCollection = (breakfasts * breakfastPrice) +
                                         (lunches * lunchPrice) +
                                         (dinners * dinnerPrice);

                report.append("Monthly Summary Report\n")
                      .append("Month: ").append(month).append("/").append(year).append("\n")
                      .append("Total Breakfasts: ").append(breakfasts).append("\n")
                      .append("Total Lunches: ").append(lunches).append("\n")
                      .append("Total Dinners: ").append(dinners).append("\n")
                      .append("Total Meals Served: ").append(breakfasts + lunches + dinners).append("\n")
                      .append("Total Collection: BDT ").append(totalCollection).append("\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report.toString();
    }
}
