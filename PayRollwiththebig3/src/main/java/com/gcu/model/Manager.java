package com.gcu.model;

public class Manager {
    private String id;
    private String username;
    private String password;
    private String role = "manager"; 

    // this is constructors
    public Manager() {}

    public Manager(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // this is the getter and seter obviously 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
}