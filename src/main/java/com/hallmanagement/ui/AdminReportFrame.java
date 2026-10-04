package com.hallmanagement.ui;

import com.hallmanagement.dao.MealSelectionDAOImpl;
import javax.swing.*;
import java.awt.*;

public class AdminReportFrame extends JFrame {
    private JTextArea reportArea;

    public AdminReportFrame(int month, int year) {
        setTitle("Admin Monthly Report");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        reportArea = new JTextArea();
        reportArea.setEditable(false);

        // ✅ Fetch real monthly summary from DAO
        MealSelectionDAOImpl dao = new MealSelectionDAOImpl();
        String report = dao.getMonthlySummary(month, year);
        reportArea.setText(report);

        add(new JScrollPane(reportArea), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminReportFrame(10, 2026).setVisible(true));
    }
}
