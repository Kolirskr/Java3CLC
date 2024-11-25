package com.gcu.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "time_sheets")
public class HourSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeSheetid")  // Ensure exact match with database column name
    private int timeSheetId;

    @Column(name = "userId")  // Exact match with database column
    private Integer userId;

    @Column(name = "hoursWorked")  // Exact match with database column
    private int hoursWorked;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "date")  // Exact match with database column
    private Date date;

    @Column(name = "isApproved")  // Exact match with database column
    private boolean isApproved;

    // Default constructor
    public HourSheet() {
    }

    // Constructor with parameters
    public HourSheet(int timeSheetId, int userId, int hoursWorked, Date date, boolean isApproved) {
        this.timeSheetId = timeSheetId;
        this.userId = userId;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.isApproved = isApproved;
    }

    public HourSheet(int userId, int hoursWorked, Date date, boolean isApproved)
    {
        this.userId = userId;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.isApproved = isApproved;
    }

    // Getters and Setters
    public int getTimeSheetId() {
        return timeSheetId;
    }

    //public void setTimeSheetId(int timeSheetId) {
    //    this.timeSheetId = timeSheetId;
    //}

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isApproved() {
        return this.isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }
}
