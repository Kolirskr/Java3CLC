package com.gcu.model;

public class Employee {
    private String id;
    private String username;
    private String password;
    private String department;
    private double salary;

    public Employee() {}

    public Employee(String id, String username, String password, String department, double salary) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.department = department;
        this.salary = salary;
    }

public String getId() { return id; }
public void setId(String id) { this.id = id; }
