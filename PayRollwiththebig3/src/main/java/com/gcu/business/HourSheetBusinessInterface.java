package com.gcu.business;

import java.util.List;

import com.gcu.model.HourSheet;

public interface HourSheetBusinessInterface 
{
    List<HourSheet> getAllHourSheets();
    boolean addHourSheet(HourSheet hourSheet);
}
