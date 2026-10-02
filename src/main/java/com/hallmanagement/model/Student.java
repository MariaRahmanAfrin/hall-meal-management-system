/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hallmanagement.model;

public class Student extends User {

    public Student() {
        super();
        setRole("STUDENT");
    }

    public Student(int id, String name, String email, String password, String phone) {
        super(id, name, email, password, "STUDENT", phone);
    }
}