package com.gcu.allcontrollers;

// this class controll and answer the request of the web. hint: the returns (home,employee-signin, manager-signin, register)
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class theController {

   
    @GetMapping
    public String home() {
        return "home"; 
    }

    
    @GetMapping("/employee-signin")
    public String employeeSignIn() {
        return "employee-signin"; 
    }

    
    @GetMapping("/manager-signin")
    public String managerSignIn() {
        return "manager-signin"; 
    }

    
    @GetMapping("/register")
    public String register() {
        return "register"; 
    }
}