package com.hallmanagement.model;

import java.sql.Date;

public class MealSelection {
    private int id;
    private int studentId;
    private Date selectionDate;
    private boolean breakfast;
    private boolean lunch;
    private boolean dinner;

    public MealSelection(int studentId, Date selectionDate, boolean breakfast, boolean lunch, boolean dinner) {
        this.studentId = studentId;
        this.selectionDate = selectionDate;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public Date getSelectionDate() { return selectionDate; }
    public void setSelectionDate(Date selectionDate) { this.selectionDate = selectionDate; }

    public boolean isBreakfast() { return breakfast; }
    public void setBreakfast(boolean breakfast) { this.breakfast = breakfast; }

    public boolean isLunch() { return lunch; }
    public void setLunch(boolean lunch) { this.lunch = lunch; }

    public boolean isDinner() { return dinner; }
    public void setDinner(boolean dinner) { this.dinner = dinner; }
}
