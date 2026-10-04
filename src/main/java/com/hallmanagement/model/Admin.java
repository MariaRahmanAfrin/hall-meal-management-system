package com.hallmanagement.model;

public class Admin extends User {
    public Admin() {
        super();
        this.setRole("ADMIN");
    }

    public Admin(int id, String name, String email, String password, String phone) {
        super(id, name, email, password, "ADMIN", phone);
    }
}