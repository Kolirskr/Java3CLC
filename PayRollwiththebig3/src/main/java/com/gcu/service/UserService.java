package com.gcu.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    @Value("${app.data-directory}")
    private String dataDirectory;

    private static final String JSON_FILE_NAME = "users.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findUserByUsername(String username) {
        try {
            Path userFilePath = Paths.get(dataDirectory, JSON_FILE_NAME);
            File file = userFilePath.toFile();

            if (!file.exists()) {
                logger.warning("User data file not found at: " + file.getAbsolutePath());
                return Optional.empty();
            }

            List<User> users = mapper.readValue(file, new TypeReference<List<User>>() {});
            return users.stream()
                        .filter(u -> u.getUsername().equalsIgnoreCase(username))
                        .findFirst();

        } catch (IOException e) {
            logger.severe("Error reading user data file: " + e.getMessage());
            return Optional.empty();
        }
    }

    public void saveUser(User user) {
        try {
            Path userFilePath = Paths.get(dataDirectory, JSON_FILE_NAME);
            File file = userFilePath.toFile();

            List<User> users = file.exists()
                ? mapper.readValue(file, new TypeReference<List<User>>() {})
                : List.of();

            // Encode password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            users.add(user);

            mapper.writeValue(file, users);
            logger.info("User saved successfully.");
        } catch (IOException e) {
            logger.severe("Error saving user: " + e.getMessage());
        }
    }

    public void encodeAndSavePasswords() {
        try {
            Path userFilePath = Paths.get(dataDirectory, JSON_FILE_NAME);
            File file = userFilePath.toFile();

            if (!file.exists()) {
                logger.warning("No user data file found to encode passwords.");
                return;
            }

            List<User> users = mapper.readValue(file, new TypeReference<List<User>>() {});
            users.forEach(user -> {
                if (!user.getPassword().startsWith("$2a$")) { // Check if already encoded
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                }
            });

            mapper.writeValue(file, users);
            logger.info("All passwords encoded successfully.");
        } catch (IOException e) {
            logger.severe("Error encoding passwords: " + e.getMessage());
        }
    }
}
