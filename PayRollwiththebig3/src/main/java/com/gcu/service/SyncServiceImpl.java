package com.gcu.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.HourSheet;
import com.gcu.model.User;
import com.gcu.repository.HourSheetRepository;
import com.gcu.repository.UserRepository;

@Service
public class SyncServiceImpl implements SyncService, ApplicationListener<ContextRefreshedEvent> 
{
    private final UserRepository userRepository;
    private final HourSheetRepository hourSheetRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path dataDir = Path.of("./data");

    public SyncServiceImpl(UserRepository userRepository, HourSheetRepository hourSheetRepository) 
    {
        this.userRepository = userRepository;
        this.hourSheetRepository = hourSheetRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) 
    {
        System.out.println("Forcing SyncServiceImpl to run on application context refresh.");
        syncDatabaseToJson();
        syncJsonToDatabase();
        System.out.println("Forced synchronization completed.");
    }

    @Override
    public void syncDatabaseToJson() 
    {
        try 
        {
            Files.createDirectories(dataDir);
            syncUsersToJson();
            syncHourSheetsToJson();
        } 
        catch (IOException e) 
        {
            System.out.println("Failed to create data directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void syncUsersToJson() 
    {
        try 
        {
            List<User> users = userRepository.findAll();
            System.out.println("Fetched " + users.size() + " users from database.");
            File userFile = dataDir.resolve("users.json").toFile();
            mapper.writeValue(userFile, users);
            System.out.println("User data written to " + userFile.getAbsolutePath());
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing users to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void syncHourSheetsToJson() 
    {
        try 
        {
            List<HourSheet> hourSheets = hourSheetRepository.findAll();
            System.out.println("Fetched " + hourSheets.size() + " time sheets from database.");
            File hourSheetFile = dataDir.resolve("hoursheets.json").toFile();
            mapper.writeValue(hourSheetFile, hourSheets);
            System.out.println("Time sheet data written to " + hourSheetFile.getAbsolutePath());
        } 
        catch (IOException e) 
        {
            System.out.println("Error writing time sheets to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void syncJsonToDatabase() 
    {
        readUsersFromJson();
        readHourSheetsFromJson();
    }

    private void readUsersFromJson() 
    {
        File userFile = dataDir.resolve("users.json").toFile();
        if (userFile.exists() && userFile.length() > 0) 
        {
            try 
            {
                List<User> users = mapper.readValue(userFile, new TypeReference<List<User>>() {});
                userRepository.saveAll(users);
                System.out.println("Users saved to database from JSON.");
            } 
            catch (IOException e) 
            {
                System.out.println("Error reading users from JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else 
        {
            System.out.println("users.json file not found or is empty.");
        }
    }

    private void readHourSheetsFromJson() 
    {
        File hourSheetFile = dataDir.resolve("hoursheets.json").toFile();
        if (hourSheetFile.exists() && hourSheetFile.length() > 0) 
        {
            try 
            {
                List<HourSheet> hourSheets = mapper.readValue(hourSheetFile, new TypeReference<List<HourSheet>>() {});
                hourSheetRepository.saveAll(hourSheets);
                System.out.println("Time sheets saved to database from JSON.");
            } 
            catch (IOException e) 
            {
                System.out.println("Error reading time sheets from JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else 
        {
            System.out.println("hoursheets.json file not found or is empty.");
        }
    }
}