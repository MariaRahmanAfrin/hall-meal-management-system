
package com.hallmanagement.ui;

import com.hallmanagement.config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.*;
import java.awt.GridLayout;

public class TodayMenuFrame extends JFrame {

    private final JLabel dayLabel = new JLabel();
    private final JLabel breakfastLabel = new JLabel("Breakfast: Loading...");
    private final JLabel lunchLabel = new JLabel("Lunch: Loading...");
    private final JLabel dinnerLabel = new JLabel("Dinner: Loading...");

    public TodayMenuFrame() {
        setTitle("Today's Menu");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        panel.add(dayLabel);
        panel.add(breakfastLabel);
        panel.add(lunchLabel);
        panel.add(dinnerLabel);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        panel.add(closeButton);

        add(panel);
        loadTodayMenu();
    }

    private void loadTodayMenu() {
        String dayName = LocalDate.now().getDayOfWeek()
            .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        dayLabel.setText("Today: " + dayName);

        String sql = "SELECT breakfast_item, lunch_item, dinner_item "
                   + "FROM weekly_menu "
                   + "WHERE LOWER(day_name) = LOWER(?)";

        try (Connection conn = DBConnection.getConnection()) {
            if (conn == null) {
                throw new SQLException("Database connection failed.");
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, dayName);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        breakfastLabel.setText(
                            "Breakfast: " + rs.getString("breakfast_item")
                        );
                        lunchLabel.setText(
                            "Lunch: " + rs.getString("lunch_item")
                        );
                        dinnerLabel.setText(
                            "Dinner: " + rs.getString("dinner_item")
                        );
                    } else {
                        breakfastLabel.setText("No menu found.");
                        lunchLabel.setText("");
                        dinnerLabel.setText("");
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Could not load menu: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new TodayMenuFrame().setVisible(true)
        );
    }
}
