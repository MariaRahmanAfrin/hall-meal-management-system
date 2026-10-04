package com.hallmanagement.util;

import com.hallmanagement.dao.MenuDAOImpl;
import com.hallmanagement.dao.MealSelectionDAOImpl;

public class MealCalculator {

    // ✅ Existing method for manual counts
    public static double calculateBill(int breakfastCount, int lunchCount, int dinnerCount) {
        double breakfastPrice = MenuDAOImpl.getMealPrice("Breakfast");
        double lunchPrice = MenuDAOImpl.getMealPrice("Lunch");
        double dinnerPrice = MenuDAOImpl.getMealPrice("Dinner");

        return (breakfastCount * breakfastPrice) +
               (lunchCount * lunchPrice) +
               (dinnerCount * dinnerPrice);
    }

    // ✅ Updated method: fetch counts from DB
    public static double calculateMonthlyBill(String studentId) {
        MealSelectionDAOImpl dao = new MealSelectionDAOImpl();

        // Get meal counts from database
        int breakfastCount = dao.getMealCountByStudent(studentId, "breakfast");
        int lunchCount = dao.getMealCountByStudent(studentId, "lunch");
        int dinnerCount = dao.getMealCountByStudent(studentId, "dinner");

        // Get prices from MenuDAOImpl
        double breakfastPrice = MenuDAOImpl.getMealPrice("Breakfast");
        double lunchPrice = MenuDAOImpl.getMealPrice("Lunch");
        double dinnerPrice = MenuDAOImpl.getMealPrice("Dinner");

        // Calculate total bill
        return (breakfastCount * breakfastPrice) +
               (lunchCount * lunchPrice) +
               (dinnerCount * dinnerPrice);
    }
}
