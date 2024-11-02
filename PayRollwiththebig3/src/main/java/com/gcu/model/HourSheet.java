package com.gcu.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_sheets")
public class HourSheet 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int timeSheetId;
    private int userId;
    private int hoursWorked;
    private Date date;
    private boolean isApproved;
    
    // Default constructor
    public HourSheet() 
    {
        
    }

    // Constructor with parameters
    public HourSheet(int timeSheetId, int userId, int hoursWorked, Date date, boolean isApproved) 
    {
        this.timeSheetId = timeSheetId;
        this.userId = userId;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.isApproved = isApproved;
    }

    // Getters and Setters
    public int getTimeSheetId() 
    {
        return timeSheetId;
    }

    public void setTimeSheetId(int timeSheetId) 
    {
        this.timeSheetId = timeSheetId;
    }

    public int getUserId() 
    {
        return userId;
    }

    public void setUserId(int userId) 
    {
        this.userId = userId;
    }

    public int getHoursWorked() 
    {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) 
    {
        this.hoursWorked = hoursWorked;
    }

    public Date getDate() 
    {
        return date;
    }

    public void setDate(Date date) 
    {
        this.date = date;
    }

    public boolean isApproved() 
    {
        return isApproved;
    }

    public void setApproved(boolean isApproved) 
    {
        this.isApproved = isApproved;
    }
}
