package com.gcu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService 
{

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Value("${app.data-directory}")
    private String dataDirectory;

    private static final String JSON_FILE_NAME = "users.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public Optional<User> findUserByUsername(String username) {
        try {
            Path userFilePath = Paths.get(dataDirectory, JSON_FILE_NAME);
            File file = userFilePath.toFile();
    
            if (!file.exists()) 
            {
                logger.warning("User data file not found at: " + file.getAbsolutePath());
                return Optional.empty();
            }
    
            List<User> users = mapper.readValue(file, new TypeReference<List<User>>() {});
            logger.info("Loaded " + users.size() + " users from JSON.");
    
            Optional<User> user = users.stream()
                                       .filter(u -> u.getUsername().equalsIgnoreCase(username)) // Case-insensitive comparison
                                       .findFirst();
    
            if (user.isPresent()) {
                logger.info("User found: " + username);
            } else {
                logger.warning("User not found: " + username);
            }
            return user;
    
        } catch (IOException e) {
            logger.severe("Error reading user data file: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
    