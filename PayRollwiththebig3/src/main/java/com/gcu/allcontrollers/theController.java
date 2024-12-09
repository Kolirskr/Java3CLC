package com.gcu.allcontrollers;

import com.gcu.model.User;
import com.gcu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class theController 
{

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public theController(UserService userService, PasswordEncoder passwordEncoder) 
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String defaultHome() 
    {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String userHome() 
    {
        return "home";
    }

    @GetMapping("/register")
    public String register(Model model) 
    {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegistration(@ModelAttribute User user) 
    {
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() 
    {
        return "login";
    }

    @GetMapping("/manager-signin")
    public String managerSignIn() 
    {
        return "manager-signin";
    }

    @GetMapping("/manager/addnew-employee")
    public String createEmployeeForm() 
    {
        return "addnew-employee";
    }
}
