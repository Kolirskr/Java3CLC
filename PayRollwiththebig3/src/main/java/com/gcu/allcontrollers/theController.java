package com.gcu.allcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class theController 
{

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

    // Manager sign-in page at /manager-signin
    @GetMapping("/manager-signin")
    public String managerSignIn() 
    {
        return "manager-signin"; // Load manager-signin.html template
    }

    // Registration page at /register
    @GetMapping("/register")
    public String register() 
    {
        return "register"; // Load register.html template
    }

    // Display hoursheet at /hoursheet (for managers)
    @GetMapping("/hoursheet")
    public String hoursheet() 
    {
        return "hoursheet"; // Load hoursheet.html template
    }

    // Display form to create a new employee (for managers)
    @GetMapping("/manager/addnew-employee")
    public String createEmployeeForm() 
    {
        return "addnew-employee"; // Load addnew-employee.html template
    }
    @GetMapping("/login")
    public String login() 
    {
        return "login"; // Load addnew-employee.html template
    }
}
