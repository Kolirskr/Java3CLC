package com.gcu.allcontrollers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import com.gcu.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class theController 
{
    private final PasswordEncoder passwordEncoder;
    private final String JSON_FILE = "users.json"; // Path to the JSON file

    // Constructor injection of PasswordEncoder
    public theController(PasswordEncoder passwordEncoder) 
    {
        this.passwordEncoder = passwordEncoder;
    }

    // Default route to redirect to the home page on startup
    @GetMapping
    public String defaultHome() 
    {
        return "redirect:/home"; // Redirect to the home page
    }

    // Home page handler
    @GetMapping("/home")
    public String home() 
    {
        return "home"; // Load home.html template
    }

    // Employee sign-in page handler
    @GetMapping("/employee-signin")
    public String employeeSignIn() 
    {
        return "employee-signin";
    }

    // Manager sign-in page handler
    @GetMapping("/manager-signin")
    public String managerSignIn() 
    {
        return "manager-signin";
    }

    // Registration page handler
    @GetMapping("/register")
    public String register() 
    {
        return "register"; // Load register.html template
    }

    // Login page handler
    @GetMapping("/login.html")
    public String login() 
    {
        return "login"; // Load login.html template
    }

    // Hoursheet page handler
    @GetMapping("/hoursheet")
    public String hoursheet() 
    {
        return "hoursheet"; // Load hoursheet.html template
    }

    // Handle user registration
    @PostMapping("/register")
    public ModelAndView registerUser(@Validated User user, BindingResult bindingResult) 
    {
        if (bindingResult.hasErrors()) 
        {
            return new ModelAndView("register"); // Show the form again if there are errors
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user to the JSON file
        saveUserToJson(user);

        System.out.println("Registered User: " + user.getUsername());

        // Redirect to login page after successful registration
        return new ModelAndView("redirect:/login.html");
    }

    // Display the form to create a new employee (for managers)
    @GetMapping("/manager/addnew-employee")
    public String createEmployeeForm() 
    {
        return "addnew-employee"; // Load addnew-employee.html template
    }

    // Process employee creation (for managers)
    @PostMapping("/manager/addnew-employee")
    public ModelAndView createEmployee(
            @RequestParam("username") String username,
            @RequestParam("password") String password) 
    {
        // Create a new Employee object
        Employee employee = new Employee();
        employee.setUsername(username); // Fixed method name
        employee.setPassword(password); // Fixed method name

        // Optional: Save the employee to the JSON file or database
        System.out.println("Manager created Employee: " + username);

        // Redirect back to the home page after creation
        return new ModelAndView("redirect:/home");
    }

    // Helper method to save user data to the JSON file
    private void saveUserToJson(User user) 
    {
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = new ArrayList<>();

        try 
        {
            // Check if the JSON file exists and load existing users
            File file = new File(JSON_FILE);
            if (file.exists()) 
            {
                users = mapper.readValue(file, new TypeReference<List<User>>() {});
            }

            // Add the new user to the list
            users.add(user);

            // Write the updated list back to the JSON file
            mapper.writeValue(file, users);
        } 
        catch (IOException e) 
        {
            System.err.println("Error saving user to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
