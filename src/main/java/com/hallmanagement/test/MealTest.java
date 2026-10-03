package com.hallmanagement.test;

import com.hallmanagement.dao.MealSelectionDAOImpl;
import com.hallmanagement.model.MealSelection;
import com.hallmanagement.util.DeadlineValidator;
import java.sql.Date;

public class MealTest {
    public static void main(String[] args) {
        MealSelectionDAOImpl dao = new MealSelectionDAOImpl();

        if (DeadlineValidator.canSelectForNextDay()) {
            MealSelection meal = new MealSelection(1, Date.valueOf("2026-10-04"), true, false, true);
            boolean saved = dao.saveSelection(meal);
            System.out.println("Meal saved: " + saved);
        } else {
            System.out.println("Cutoff time passed. Cannot select for next day.");
        }

        dao.getSelectionsByStudent(1).forEach(m ->
            System.out.println("Date: " + m.getSelectionDate() +
                " Breakfast: " + m.isBreakfast() +
                " Lunch: " + m.isLunch() +
                " Dinner: " + m.isDinner())
        );
    }
}
