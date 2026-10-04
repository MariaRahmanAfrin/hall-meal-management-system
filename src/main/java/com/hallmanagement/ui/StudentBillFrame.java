package com.hallmanagement.ui;

import javax.swing.*;
import java.awt.*;
import com.hallmanagement.util.MealCalculator;


public class StudentBillFrame extends JFrame {
    public StudentBillFrame() {
        setTitle("Monthly Bill");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel billLabel = new JLabel("Calculating...", SwingConstants.CENTER);
        billLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // TODO: Replace with DB integration
        double totalBill = MealCalculator.calculateMonthlyBill("student123");
        billLabel.setText("Total Monthly Bill: BDT " + totalBill);

        add(billLabel, BorderLayout.CENTER);}
        public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new StudentBillFrame().setVisible(true));
}

    }

