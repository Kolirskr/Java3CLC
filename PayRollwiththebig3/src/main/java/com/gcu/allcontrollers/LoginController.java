package com.gcu.allcontrollers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.logging.Logger;

@Controller
public class LoginController 
{

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) 
    {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/login.html")
    public String loginPage() 
    {
        return "login";
    }

    @PostMapping("/perform_login")
    public ModelAndView loginUser(@RequestParam String username, @RequestParam String password) 
    {
        try 
        {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

            if (authentication.isAuthenticated()) 
            {
                return new ModelAndView("redirect:/home");
            }
        } 
        catch (AuthenticationException e) 
        {
            logger.warning("Login failed for user: " + username);
        }

        return new ModelAndView("redirect:/login.html?error=true");
    }
}
