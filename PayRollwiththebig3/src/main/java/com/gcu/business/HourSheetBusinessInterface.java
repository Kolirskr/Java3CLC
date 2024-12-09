package com.gcu.business;

import com.gcu.model.HourSheet;

import java.util.List;
import java.util.Optional;

public interface HourSheetBusinessInterface 
{
    List<HourSheet> getAllHourSheets();

    boolean addHourSheet(HourSheet hourSheet);

    void removeHourSheet(Integer timeSheetId);

    void editTimeSheet(Integer timeSheetId, Integer newHours);

    List<HourSheet> getAllHourSheetsAPI();

    Optional<HourSheet> getHourSheetByIdAPI(int id);
}
