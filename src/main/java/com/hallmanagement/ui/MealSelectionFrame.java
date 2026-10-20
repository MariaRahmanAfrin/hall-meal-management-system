package com.hallmanagement.ui;

import com.hallmanagement.util.DeadlineValidator;
import com.hallmanagement.util.MealCalculator;
import com.hallmanagement.exception.DeadlineExceededException;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MealSelectionFrame extends JFrame {
    private JCheckBox breakfastBox, lunchBox, dinnerBox;
    private JButton submitButton;

    public MealSelectionFrame() {
        setTitle("Meal Selection");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1)); // 5 rows now

        // Show next day's date
        JLabel dateLabel = new JLabel("Meal Selection for: " + LocalDate.now().plusDays(1));
        breakfastBox = new JCheckBox("Breakfast");
        lunchBox = new JCheckBox("Lunch");
        dinnerBox = new JCheckBox("Dinner");
        submitButton = new JButton("Submit");

        add(dateLabel);
        add(breakfastBox);
        add(lunchBox);
        add(dinnerBox);
        add(submitButton);

        // Deadline check
        if (!DeadlineValidator.canSelectForNextDay()) {
            breakfastBox.setEnabled(false);
            lunchBox.setEnabled(false);
            dinnerBox.setEnabled(false);
            submitButton.addActionListener(e -> {
                try {
                    throw new DeadlineExceededException("Deadline passed! You cannot select meals now.");
                } catch (DeadlineExceededException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        } else {
            submitButton.addActionListener(e -> {
                int breakfastCount = breakfastBox.isSelected() ? 1 : 0;
                int lunchCount = lunchBox.isSelected() ? 1 : 0;
                int dinnerCount = dinnerBox.isSelected() ? 1 : 0;

                double bill = MealCalculator.calculateBill(breakfastCount, lunchCount, dinnerCount);
                JOptionPane.showMessageDialog(this, "Your total bill: " + bill, "Success", JOptionPane.INFORMATION_MESSAGE);
            });
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        new MealSelectionFrame();
    }
}
