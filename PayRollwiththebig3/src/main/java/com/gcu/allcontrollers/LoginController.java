package com.gcu.allcontrollers;

import java.util.logging.Logger;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gcu.service.UserService;

@Controller
public class LoginController 
{
    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
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
                new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) 
            {
                // Redirect based on role
                for (GrantedAuthority authority : authentication.getAuthorities()) 
                {
                    if ("ROLE_MANAGER".equals(authority.getAuthority())) 
                    {
                        return new ModelAndView("redirect:/hoursheet");
                    }
                }
                // Default redirection
                return new ModelAndView("redirect:/home");
            }
        } 
        catch (AuthenticationException e) 
        {
            logger.warning("Login failed for user: " + username + ". Reason: " + e.getMessage());
        }

        return new ModelAndView("redirect:/login.html?error=true");
    }
}
