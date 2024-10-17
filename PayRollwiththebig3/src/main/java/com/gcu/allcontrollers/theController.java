package com.gcu.allcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import com.gcu.model.User;
import com.gcu.model.Employee;

@Controller
@RequestMapping("/")
public class theController 
{
    @GetMapping("/home")
    public String home() 
    {
        // Instead of redirecting, render the "home.html" view directly
        return "home"; 
    }

    @GetMapping("/employee-signin")
    public String employeeSignIn() 
    {
        return "employee-signin"; 
    }

    @GetMapping("/manager-signin")
    public String managerSignIn() 
    {
        return "manager-signin"; 
    }

    @GetMapping("/register")
    public String register() 
    {
        return "register"; // Load the registration page
    }

    @GetMapping("/hoursheet")
    public String hoursheet() 
    {
        return "hoursheet"; // Load the hoursheet page (assuming it exists)
    }

    @GetMapping("/login")
    public String login() 
    {
        // Render the login page directly, instead of redirecting
        return "login"; 
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Validated User user, BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            return new ModelAndView("register");
        }

        // Process the user data (e.g., saving the user to the database)
        System.out.println("Registered User: " + user.getUsername());

        // After successful registration, redirect to the login page
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/manager/addnew-employee")
    public String createEmployeeForm() 
    {
        return "addnew-employee"; // Return the form for adding a new employee
    }

    @PostMapping("/manager/addnew-employee")
    public ModelAndView createEmployee(
            @RequestParam("username") String username,
            @RequestParam("password") String password) 
    {
        // Manager creates a new employee
        Employee employee = new Employee();
        employee.setusername(username);
        employee.setpassword(password);

        // Optional: Save the employee to the database or perform other logic
        System.out.println("Manager created Employee: " + username);

        // After creation, redirect the manager back to the home page ("/")
        return new ModelAndView("redirect:/home");
    }
}
