package com.gcu.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.HourSheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class HourSheetBusinessService implements HourSheetBusinessInterface {

    private final String dataDirectory; // Data directory path

    private List<HourSheet> hourSheets = new ArrayList<>();

    // Constructor injection
    public HourSheetBusinessService(@Value("${app.data-directory}") String dataDirectory) {
        this.dataDirectory = dataDirectory;
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
        // Ensure dataDirectory is not null
        if (dataDirectory == null) {
            System.err.println("Data directory is not set.");
            return;
        }
        // Construct the path to hoursheets.json in the data directory
        Path hourSheetFilePath = Paths.get(dataDirectory, "hoursheets.json");
        File file = hourSheetFilePath.toFile();

        if (file.exists()) {
            try {
                hourSheets = mapper.readValue(file, new TypeReference<List<HourSheet>>() {});
            } catch (IOException e) {
                System.err.println("Error loading hour sheets: " + e.getMessage());
            }
        } else {
            System.out.println("No existing hour sheets found. Starting with an empty list.");
        }
    }

    private void saveHourSheetsToJson() {
        ObjectMapper mapper = new ObjectMapper();
        // Ensure dataDirectory is not null
        if (dataDirectory == null) {
            System.err.println("Data directory is not set.");
            return;
        }
        // Construct the path to hoursheets.json in the data directory
        Path hourSheetFilePath = Paths.get(dataDirectory, "hoursheets.json");
        File file = hourSheetFilePath.toFile();

        try {
            // Ensure the data directory exists
            File dataDir = file.getParentFile();
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            mapper.writeValue(file, hourSheets);
        } catch (IOException e) {
            System.err.println("Error saving hour sheets: " + e.getMessage());
        }
    }
}
