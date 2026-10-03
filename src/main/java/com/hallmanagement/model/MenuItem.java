package com.hallmanagement.model;

public class MenuItem {
    private String dayName;
    private String breakfastItem;
    private String lunchItem;
    private String dinnerItem;

    public MenuItem(String dayName, String breakfastItem, String lunchItem, String dinnerItem) {
        this.dayName = dayName;
        this.breakfastItem = breakfastItem;
        this.lunchItem = lunchItem;
        this.dinnerItem = dinnerItem;
    }

    public String getDayName() { return dayName; }
    public void setDayName(String dayName) { this.dayName = dayName; }

    public String getBreakfastItem() { return breakfastItem; }
    public void setBreakfastItem(String breakfastItem) { this.breakfastItem = breakfastItem; }

    public String getLunchItem() { return lunchItem; }
    public void setLunchItem(String lunchItem) { this.lunchItem = lunchItem; }

    public String getDinnerItem() { return dinnerItem; }
    public void setDinnerItem(String dinnerItem) { this.dinnerItem = dinnerItem; }
}