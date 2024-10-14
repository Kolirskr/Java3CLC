package com.gcu.model;

public class HourSheet 
{
    private String employeeName;
    private int hoursWorked;

    public HourSheet(String employeeName, int hoursWorked) 
    {
        this.employeeName = employeeName;
        this.hoursWorked = hoursWorked;
    }

    public String getEmployeeName() 
    {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) 
    {
        this.employeeName = employeeName;
    }

    public int getHoursWorked() 
    {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) 
    {
        this.hoursWorked = hoursWorked;
    }
}
