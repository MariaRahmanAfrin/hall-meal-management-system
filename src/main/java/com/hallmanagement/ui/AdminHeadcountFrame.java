
package com.hallmanagement.ui;

import com.hallmanagement.dao.MealSelectionDAOImpl;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.LocalDate;
import javax.swing.*;

public class AdminHeadcountFrame extends JFrame {

    private final JTextField dateField =
            new JTextField(LocalDate.now().toString());

    private final JLabel breakfastCount = new JLabel("0");
    private final JLabel lunchCount = new JLabel("0");
    private final JLabel dinnerCount = new JLabel("0");

    private final MealSelectionDAOImpl dao =
            new MealSelectionDAOImpl();

    public AdminHeadcountFrame() {
        setTitle("Daily Meal Headcount");
        setSize(450, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(dateField);

        panel.add(new JLabel("Breakfast YES:"));
        panel.add(breakfastCount);

        panel.add(new JLabel("Lunch YES:"));
        panel.add(lunchCount);

        panel.add(new JLabel("Dinner YES:"));
        panel.add(dinnerCount);

        JButton loadButton = new JButton("Load Headcount");
        JButton backButton = new JButton("Back");

        panel.add(loadButton);
        panel.add(backButton);

        loadButton.addActionListener(e -> loadHeadcount());

        backButton.addActionListener(e -> dispose());

        add(panel);
        loadHeadcount();
    }

    private void loadHeadcount() {
        try {
            Date date = Date.valueOf(dateField.getText().trim());
            int[] counts = dao.getHeadcountByDate(date);

            breakfastCount.setText(String.valueOf(counts[0]));
            lunchCount.setText(String.valueOf(counts[1]));
            dinnerCount.setText(String.valueOf(counts[2]));

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter date as YYYY-MM-DD.",
                    "Invalid Date",
                    JOptionPane.WARNING_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Could not load headcount. Check database connection.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new AdminHeadcountFrame().setVisible(true));
    }
}

