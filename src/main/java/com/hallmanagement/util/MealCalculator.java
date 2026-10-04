package com.hallmanagement.util;

import com.hallmanagement.dao.MenuDAOImpl;

public class MealCalculator {

    public static double calculateBill(int breakfastCount, int lunchCount, int dinnerCount) {
        double breakfastPrice = MenuDAOImpl.getMealPrice("Breakfast");
        double lunchPrice = MenuDAOImpl.getMealPrice("Lunch");
        double dinnerPrice = MenuDAOImpl.getMealPrice("Dinner");

        return (breakfastCount * breakfastPrice) +
               (lunchCount * lunchPrice) +
               (dinnerCount * dinnerPrice);
    }
}
