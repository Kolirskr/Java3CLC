package com.gcu.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcu.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Allow plain text passwords
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig 
{
    private static final String JSON_FILE = "users.json"; // Path to JSON file

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/home","/login.html", "/register", "/css/**").permitAll() // Allow public access
                .requestMatchers("/hoursheet.html").hasRole("MANAGER") // Only Manager can access hoursheet
                .anyRequest().authenticated()) // All other requests require authentication
            .formLogin(form -> form
                .loginPage("/login.html") // Custom login page
                .loginProcessingUrl("/perform_login") // Login processing URL
                .defaultSuccessUrl("/home", true) // Redirect to home on success
                .failureUrl("/login.html?error=true")) // Redirect back to login on failure
            .logout(logout -> logout
                .logoutUrl("/logout") // Logout URL
                .logoutSuccessUrl("/login.html")); // Redirect to login on logout

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() 
    {
        return username -> 
        {
            User user = findUserByUsername(username); // Load user from JSON

            if (user == null) 
            {
                throw new UsernameNotFoundException("User not found: " + username);
            }

            // Convert the User object to a Spring Security UserDetails object
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword()) // Use plain text password
                    .roles(user.getRole().toUpperCase()) // Ensure role matches expected format
                    .build();
        };
    }

    // Helper method to load users from the JSON file
    private User findUserByUsername(String username) 
    {
        ObjectMapper mapper = new ObjectMapper();
        try 
        {
            List<User> users = mapper.readValue(new File(JSON_FILE), new TypeReference<List<User>>() {});
            for (User user : users) 
            {
                if (user.getUsername().equals(username)) 
                {
                    return user; // Return the matching user
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return null; // User not found
    }

    @Bean
    public PasswordEncoder passwordEncoder() 
    {
        // Use NoOpPasswordEncoder to allow plain text passwords
        return NoOpPasswordEncoder.getInstance();
    }
}
