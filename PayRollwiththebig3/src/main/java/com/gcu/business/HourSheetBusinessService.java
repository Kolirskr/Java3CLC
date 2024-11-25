package com.gcu.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.HourSheet;
import com.gcu.repository.HourSheetRepository;

@Service
public class HourSheetBusinessService implements HourSheetBusinessInterface {

    @Autowired
    private HourSheetRepository hourSheetRepository;

    private final String dataDirectory; // Data directory path

    private List<HourSheet> hourSheets = new ArrayList<>();

    // Constructor injection
    public HourSheetBusinessService(@Value("${app.data-directory}") String dataDirectory) {
        this.dataDirectory = dataDirectory;
        loadHourSheetsFromJson(); // Load existing hour sheets on startup
    }

    @Override
    public List<HourSheet> getAllHourSheets() {
        loadHourSheetsFromJson(); // Ensure we reload the latest data each time
        return hourSheets;
    }

    @Override
    public boolean addHourSheet(HourSheet hourSheet) {
    // Save the HourSheet to the database first
    HourSheet savedHourSheet = hourSheetRepository.save(hourSheet); // This updates the hourSheet with the generated ID

    // Add the saved HourSheet (with timeSheetId) to the in-memory list
    boolean added = hourSheets.add(savedHourSheet);

    if (added) {
        saveHourSheetsToJson(); // Save the updated list to JSON
    }

    return added;
    }
    

    @Override
    public void removeHourSheet(Integer timeSheetId)
    {
         // Delete from the database
         hourSheetRepository.deleteById(timeSheetId);

         // Update the JSON file
         String fileName = "data/hoursheets.json";
         try {
             String content = new String(Files.readAllBytes(Paths.get(fileName)));
             JSONArray jsonArray = new JSONArray(content);
             JSONArray updatedArray = new JSONArray();
 
             for (int i = 0; i < jsonArray.length(); i++) {
                 JSONObject entry = jsonArray.getJSONObject(i);
                 if (entry.getInt("timeSheetId") != timeSheetId) {
                     updatedArray.put(entry);
                 }
             }
 
             Files.write(Paths.get(fileName), updatedArray.toString(4).getBytes());
         } catch (IOException e) {
             throw new RuntimeException("Error updating JSON file: " + e.getMessage(), e);
         }
     }

    @Override
    public void editTimeSheet(Integer timeSheetId, Integer newHours)
    {
        String fileName = "data/hoursheets.json";

        try {
            // Read the JSON file content as a string
            String content = new String(Files.readAllBytes(Paths.get(fileName)));

            // Parse the string content as a JSON array
            JSONArray jsonArray = new JSONArray(content);

            // Flag to track if the timesheet was found
            boolean found = false;

            // Iterate through the JSON array to find the timesheet by timeSheetId
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject entry = jsonArray.getJSONObject(i);

                // Check if the current entry's timeSheetId matches
                if (entry.getInt("timeSheetId") == timeSheetId) {
                    // Update the hoursWorked field
                    entry.put("hoursWorked", newHours);
                    found = true;
                    break;
                }
            }

            // If the timesheet was found, write the updated JSON array back to the file
            if (found) {
                Files.write(Paths.get(fileName), jsonArray.toString(4).getBytes());
                System.out.println("Timesheet with ID " + timeSheetId + " has been updated to " + newHours + " hours.");
            } else {
                System.out.println("Timesheet with ID " + timeSheetId + " was not found.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void loadHourSheetsFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        Path hourSheetFilePath = Paths.get(dataDirectory, "hoursheets.json");
        File file = hourSheetFilePath.toFile();

        // Ensure dataDirectory is not null
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
    
            // Write the current state of hourSheets to the JSON file
            mapper.writeValue(file, hourSheets);
        } catch (IOException e) {
            System.err.println("Error saving hour sheets: " + e.getMessage());
        }
    }
}
