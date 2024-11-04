package com.gcu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.HourSheet;
import com.gcu.model.User;
import com.gcu.repository.HourSheetRepository;
import com.gcu.repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class SyncServiceImpl implements SyncService, ApplicationListener<ContextRefreshedEvent> 
{
    private final UserRepository userRepository;
    private final HourSheetRepository hourSheetRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    private final Path dataDir = Path.of("./data");
    private static final Logger logger = Logger.getLogger(SyncServiceImpl.class.getName());

    public SyncServiceImpl(UserRepository userRepository, HourSheetRepository hourSheetRepository) 
    {
        this.userRepository = userRepository;
        this.hourSheetRepository = hourSheetRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) 
    {
        logger.info("Initializing SyncServiceImpl and running sync on application startup.");
        syncDatabaseToJson();
        syncJsonToDatabase();
        logger.info("Initial synchronization completed.");
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
            logger.warning("Failed to create data directory: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void syncUsersToJson() 
    {
        try 
        {
            List<User> currentUsers = userRepository.findAll();
            logger.info("Fetched " + currentUsers.size() + " users from database.");

            // Load existing users from JSON file, if it exists
            File userFile = dataDir.resolve("users.json").toFile();
            List<User> allUsers = loadExistingData(userFile, new TypeReference<List<User>>() {});
            
            // Add only new users to the existing list
            for (User user : currentUsers) {
                if (!allUsers.contains(user)) {
                    allUsers.add(user);
                }
            }

            mapper.writeValue(userFile, allUsers);
            logger.info("User data written to " + userFile.getAbsolutePath());
        } 
        catch (IOException e) 
        {
            logger.warning("Error writing users to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void syncHourSheetsToJson() 
    {
        try 
        {
            List<HourSheet> currentHourSheets = hourSheetRepository.findAll();
            logger.info("Fetched " + currentHourSheets.size() + " time sheets from database.");

            // Load existing time sheets from JSON file, if it exists
            File hourSheetFile = dataDir.resolve("hoursheets.json").toFile();
            List<HourSheet> allHourSheets = loadExistingData(hourSheetFile, new TypeReference<List<HourSheet>>() {});
            
            // Add only new time sheets to the existing list
            for (HourSheet hourSheet : currentHourSheets) {
                if (!allHourSheets.contains(hourSheet)) {
                    allHourSheets.add(hourSheet);
                }
            }

            mapper.writeValue(hourSheetFile, allHourSheets);
            logger.info("Time sheet data written to " + hourSheetFile.getAbsolutePath());
        } 
        catch (IOException e) 
        {
            logger.warning("Error writing time sheets to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private <T> List<T> loadExistingData(File file, TypeReference<List<T>> typeReference) 
    {
        List<T> dataList = new ArrayList<>();
        if (file.exists()) 
        {
            try 
            {
                dataList = mapper.readValue(file, typeReference);
                logger.info("Loaded " + dataList.size() + " existing records from " + file.getAbsolutePath());
            } 
            catch (IOException e) 
            {
                logger.warning("Error loading existing data from " + file.getAbsolutePath() + ": " + e.getMessage());
            }
        }
        return dataList;
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
                logger.info("Users saved to database from JSON.");
            } 
            catch (IOException e) 
            {
                logger.warning("Error reading users from JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else 
        {
            logger.warning("users.json file not found or is empty.");
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
                logger.info("Time sheets saved to database from JSON.");
            } 
            catch (IOException e) 
            {
                logger.warning("Error reading time sheets from JSON: " + e.getMessage());
                e.printStackTrace();
            }
        } 
        else 
        {
            logger.warning("hoursheets.json file not found or is empty.");
        }
    }

    public void addUserAndSync(User user) 
    {
        userRepository.save(user); // Save new user to DB
        syncUsersToJson();         // Sync all users to JSON
        logger.info("User added and synchronized to JSON.");
    }

    public void addHourSheetAndSync(HourSheet hourSheet) 
    {
        hourSheetRepository.save(hourSheet); // Save new timesheet to DB
        syncHourSheetsToJson();              // Sync all timesheets to JSON
        logger.info("Time sheet added and synchronized to JSON.");
    }
}
