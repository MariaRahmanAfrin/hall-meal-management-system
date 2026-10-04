package com.hallmanagement.ui;

import com.hallmanagement.dao.MealSelectionDAOImpl;
import com.hallmanagement.model.MealSelection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MealHistoryFrame extends JFrame {
    private JTable historyTable;

    public MealHistoryFrame(String studentId) {
        setTitle("Meal History");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columns = {"Date", "Breakfast", "Lunch", "Dinner"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // ✅ Fetch real data from DAO
        MealSelectionDAOImpl dao = new MealSelectionDAOImpl();
        List<MealSelection> meals = dao.getMealHistoryByStudent(studentId);

        for (MealSelection m : meals) {
            model.addRow(new Object[]{
                m.getSelectionDate(),
                m.isBreakfast() ? "Yes" : "No",
                m.isLunch() ? "Yes" : "No",
                m.isDinner() ? "Yes" : "No"
            });
        }

        historyTable = new JTable(model);
        add(new JScrollPane(historyTable), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MealHistoryFrame("student123").setVisible(true));
    }
}
