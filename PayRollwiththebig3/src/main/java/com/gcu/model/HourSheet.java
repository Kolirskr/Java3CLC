package com.gcu.model;

public class HourSheet 
{
private int hours;
private String approval;

public HourSheet( int hours){
    this.hours = hours;
    this.approval = "pending"; // for at the begining normal when no approval yet 

}
public int getHours(){
    return hours;

}
public void setHours (int hours) {
        this.hours= hours;
}
public String getApproval(){
    return approval;
}
public void setApproval( String approval){
    this.approval= approval;
}
}