package com.hallmanagement.model;

import java.sql.Date;

public class DailyExpense {
    private int id;
    private int studentId;
    private Date expenseDate;
    private double amount;
    private String description;

    public DailyExpense(int studentId, Date expenseDate, double amount, String description) {
        this.studentId = studentId;
        this.expenseDate = expenseDate;
        this.amount = amount;
        this.description = description;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public Date getExpenseDate() { return expenseDate; }
    public void setExpenseDate(Date expenseDate) { this.expenseDate = expenseDate; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
