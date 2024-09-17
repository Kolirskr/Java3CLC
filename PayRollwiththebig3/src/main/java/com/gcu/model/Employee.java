package com.gcu.model;

public class Employee {
    private String id;
    private String username;
    private String password;
    private String department;
    private double salary;

    public Employee() {}

    public Employee(String id, String username, String password, String department, double salary) {
        this.id = id;//lol
        this.username = username;
        this.password = password;
        this.department = department;
        this.salary = salary;
    }

public String getId() { return id; }
public void setId(String id) { this.id = id; 

}
public String getusername() { return username; }
public void setusername(String username) { this.username= username;
}

public String getpassword() { return password; }
public void setpassword(String password) { this.password = password;
}

public String getdepartment() { return  department; }
public void setdepartment(String department) { this.department = department;
}
public double getsalary() { return  salary; }
public void setsalary(double salary) { this.salary = salary;
}
}
