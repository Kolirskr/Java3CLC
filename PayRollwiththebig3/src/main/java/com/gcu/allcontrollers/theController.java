package com.gcu.allcontrollers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class theController 
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Redirect to /user-home when accessing the root "/"
    @GetMapping("/")
    public String defaultHome() 
    {
        return "redirect:/user-home";
    }

    // Display the user home page at /user-home
    @GetMapping("/user-home")
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
    public String handleRegistration(@ModelAttribute("user") User user) 
    {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the users.json file
        saveUserToJson(user);

        // Redirect to the login page after successful registration
        return "redirect:/login";
    }

    // Helper method to save user to users.json
    private void saveUserToJson(User user) 
    {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("users.json");

        try 
        {
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

            // Add the new user
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