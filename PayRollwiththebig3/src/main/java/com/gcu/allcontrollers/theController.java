package com.gcu.allcontrollers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class theController 
{

    private final PasswordEncoder passwordEncoder;
    private final String dataDirectory;

    public theController(PasswordEncoder passwordEncoder, @Value("${app.data-directory}") String dataDirectory) 
    {
        this.passwordEncoder = passwordEncoder;
        this.dataDirectory = dataDirectory;
    }

    // Redirect to /user-home when accessing the root "/"
    @GetMapping("/")
    public String defaultHome() 
    {
        return "redirect:/home";
    }

    // Display the user home page at /user-home
    @GetMapping("/home")
    public String userHome() 
    {
        return "home"; // Load home.html template
    }

    // Registration page at /register (GET request)
    @GetMapping("/register")
    public String register(Model model) 
    {
        model.addAttribute("user", new User()); // Add a new User object to the model
        return "register"; // Load register.html template
    }

    // Handle registration form submission (POST request)
    @PostMapping("/register")
    public String handleRegistration(@ModelAttribute User user) 
    {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the users.json file
        saveUserToJson(user);

        // Redirect to the login page after successful registration
        return "redirect:/login";
    }

    // Helper method to save user to users.json with a unique ID
    private void saveUserToJson(User user) {
        ObjectMapper mapper = new ObjectMapper();
        // Construct the path to users.json in the data directory
        Path userFilePath = Paths.get(dataDirectory, "users.json");
        File file = userFilePath.toFile();

        try 
        {
            // Ensure the data directory exists
            File dataDir = file.getParentFile();
            if (!dataDir.exists()) 
            {
                dataDir.mkdirs();
            }

            List<User> users;
            if (file.exists()) 
            {
                // Read existing users
                users = mapper.readValue(file, new TypeReference<List<User>>() {});
            } 
            else 
            {
                users = new ArrayList<>();
            }

            // Determine the next ID by finding the highest existing ID
            Integer nextId = (int) (users.stream()
                               .mapToLong(u -> u.getId() == null ? 0 : u.getId())
                               .max()
                               .orElse(0) + 1);

            // Set the user's ID to the next available ID
            user.setId(nextId);

            // Add the new user to the list
            users.add(user);

            // Save back to the JSON file
            mapper.writeValue(file, users);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    // Login page at /login
    @GetMapping("/login")
    public String login() 
    {
        return "login"; // Load login.html template
    }

    // Manager sign-in page at /manager-signin
    @GetMapping("/manager-signin")
    public String managerSignIn() 
    {
        return "manager-signin"; // Load manager-signin.html template
    }

    // Display form to create a new employee (for managers)
    @GetMapping("/manager/addnew-employee")
    public String createEmployeeForm() 
    {
        return "addnew-employee"; // Load addnew-employee.html template
    }
}
