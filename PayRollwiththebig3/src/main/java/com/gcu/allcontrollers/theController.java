package com.gcu.allcontrollers;

// this class controll and answer the request of the web. hint: the returns (home,employee-signin, manager-signin, register)
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcu.model.Employee;
import com.gcu.model.User;
@EnableWebSecurity
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
    //this one handle the user Registration 
    // and if theres error retur to register page
    @PostMapping("/register")
    public ModelAndView registerUser(@Validated User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
       return new ModelAndView ("register");
        }
        //processing the user data
     System.out.println("Registered User:" + user.getUsername());
      //after that success register return to home page
     return new ModelAndView("redirect:/");
    }

    
    @GetMapping("/manager/addnew-employee") // when manager signs in it can add new employee 
    public String createEmployeeForm() {
        return "addnew-employee"; 
    }
//  the manager deals with addin new employee
    @PostMapping("/manager/addnew-employee")
    public ModelAndView createEmployee(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        // Manager add a new employee
        Employee employee = new Employee();
        employee.setusername(username);
        employee.setpassword(password);

        // extra things can be added for exampl saving the employee to a database
        System.out.println("Manager created Employee: " + username);
        //after done send the manager back to his dashboard or to the beginnig page
        return new ModelAndView("redirect:/");
    }
        
 }

