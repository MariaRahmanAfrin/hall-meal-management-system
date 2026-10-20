package com.hallmanagement.model;

public class MealPrice {
    private double breakfastPrice;
    private double lunchPrice;
    private double dinnerPrice;

    public MealPrice(double breakfastPrice, double lunchPrice, double dinnerPrice) {
        this.breakfastPrice = breakfastPrice;
        this.lunchPrice = lunchPrice;
        this.dinnerPrice = dinnerPrice;
    }

    public double getBreakfastPrice() { return breakfastPrice; }
    public void setBreakfastPrice(double breakfastPrice) { this.breakfastPrice = breakfastPrice; }

    public double getLunchPrice() { return lunchPrice; }
    public void setLunchPrice(double lunchPrice) { this.lunchPrice = lunchPrice; }

    public double getDinnerPrice() { return dinnerPrice; }
    public void setDinnerPrice(double dinnerPrice) { this.dinnerPrice = dinnerPrice; }
}