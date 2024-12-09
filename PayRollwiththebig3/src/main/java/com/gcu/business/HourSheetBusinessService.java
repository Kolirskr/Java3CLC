package com.gcu.business;

import com.gcu.model.HourSheet;
import com.gcu.repository.HourSheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HourSheetBusinessService implements HourSheetBusinessInterface 
{

    private final HourSheetRepository hourSheetRepository;

    @Autowired
    public HourSheetBusinessService(HourSheetRepository hourSheetRepository) 
    {
        this.hourSheetRepository = hourSheetRepository;
    }

    @Override
    public List<HourSheet> getAllHourSheets() 
    {
        return hourSheetRepository.findAll();
    }

    @Override
    public List<HourSheet> getAllHourSheetsAPI() 
    {
        return hourSheetRepository.findAll();
    }

    @Override
    public Optional<HourSheet> getHourSheetByIdAPI(int id) 
    {
        return hourSheetRepository.findById(id);
    }

    @Override
    public boolean addHourSheet(HourSheet hourSheet) 
    {
        hourSheetRepository.save(hourSheet);
        return true;
    }

    @Override
    public void removeHourSheet(Integer timeSheetId) 
    {
        hourSheetRepository.deleteById(timeSheetId);
    }

    @Override
    public void editTimeSheet(Integer timeSheetId, Integer newHours) 
    {
        HourSheet hourSheet = hourSheetRepository.findById(timeSheetId)
                .orElseThrow(() -> new RuntimeException("HourSheet not found"));
        hourSheet.setHoursWorked(newHours);
        hourSheetRepository.save(hourSheet);
    }
}
