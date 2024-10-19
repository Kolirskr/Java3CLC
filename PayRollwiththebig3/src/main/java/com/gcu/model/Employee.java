package com.gcu.model;

public class Employee {
    private String id;
    private String username;
    private String password;
    private String department;
    private double salary;

    // Default constructor
    public Employee() {}

    // Parameterized constructor
    public Employee(String id, String username, String password, String department, double salary) 
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.department = department;
        this.salary = salary;
    }

    // Getter and setter for id
    public String getId() { 
        return id; 
    }
    public void setId(String id) { 
        this.id = id; 
    }

    // Getter and setter for username
    public String getUsername() { 
        return username; 
    }
    public void setUsername(String username) { 
        this.username = username; 
    }

    // Getter and setter for password
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }

    // Getter and setter for department
    public String getDepartment() { 
        return department; 
    }
    public void setDepartment(String department) { 
        this.department = department; 
    }

    // Getter and setter for salary
    public double getSalary() { 
        return salary; 
    }
    public void setSalary(double salary) { 
        this.salary = salary; 
    }
}
