package com.gcu.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.HourSheet;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HourSheetBusinessService implements HourSheetBusinessInterface {

    private static final String JSON_FILE = "hoursheets.json"; // Path to the JSON file
    private List<HourSheet> hourSheets = new ArrayList<>();

    public HourSheetBusinessService() {
        loadHourSheetsFromJson(); // Load existing hour sheets on startup
    }

    @Override
    public List<HourSheet> getAllHourSheets() {
        return hourSheets;
    }

    @Override
    public boolean addHourSheet(HourSheet hourSheet) {
        boolean added = hourSheets.add(hourSheet);
        if (added) {
            saveHourSheetsToJson(); // Save to JSON after adding
        }
        return added;
    }

    private void loadHourSheetsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(JSON_FILE);
        if (file.exists()) {
            try {
                hourSheets = mapper.readValue(file, new TypeReference<List<HourSheet>>() {});
            } catch (IOException e) {
                System.err.println("Error loading hour sheets: " + e.getMessage());
            }
        }
    }

    private void saveHourSheetsToJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(JSON_FILE), hourSheets);
        } catch (IOException e) {
            System.err.println("Error saving hour sheets: " + e.getMessage());
        }
    }
}
