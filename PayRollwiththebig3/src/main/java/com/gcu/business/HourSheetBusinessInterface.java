package com.gcu.business;

import java.util.List;

import com.gcu.model.HourSheet;

public interface HourSheetBusinessInterface 
{
    List<HourSheet> getAllHourSheets();
    boolean addHourSheet(HourSheet hourSheet);

    // Needs to be implemented
    void removeHourSheet(Integer timeSheetId);
    void editTimeSheet(Integer timeSheetId, Integer newHours);
}
