package com.gcu.allcontrollers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class LoginController 
{
    private final PasswordEncoder passwordEncoder;

    // Constructor-based Dependency Injection
    public LoginController(PasswordEncoder passwordEncoder) 
    {
        this.passwordEncoder = passwordEncoder;
    }

    // Display the login page at /login.html
    @GetMapping("/login.html")
    public String loginPage() 
    {
        return "login"; // Load the login.html template
    }

    // Role-based redirect after successful login
    @GetMapping("/home")
    public String homeRedirect(Authentication authentication) 
    {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MANAGER"))) 
        {
            return "redirect:/hoursheet"; // Redirect manager to the hoursheet
        }
        return "redirect:/employee-dashboard.html"; // Redirect employee to dashboard
    }

    // Handle the login form submission at /perform_login
    @PostMapping("/perform_login")
    public ModelAndView loginUser(
        @RequestParam("username") String username,
        @RequestParam("password") String password) 
    {
        User user = findUserByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) 
        {
            System.out.println("Login successful: " + user.getUsername() + " (" + user.getRole() + ")");
            return new ModelAndView("redirect:/home"); // Redirect based on role
        }

        // Redirect back to login page with an error message
        return new ModelAndView("redirect:/login.html?error=true");
    }

    // Find a user by username from the users.json file
    private User findUserByUsername(String username) 
    {
        ObjectMapper mapper = new ObjectMapper();
        try 
        {
            // Load users from JSON file
            List<User> users = mapper.readValue(new File("users.json"), new TypeReference<List<User>>() {});
            for (User user : users) 
            {
                if (user.getUsername().equals(username)) 
                {
                    return user; // User found, return it
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace(); // Print error for troubleshooting
        }
        return null; // User not found
    }
}
