package com.gcu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.gcu.service.UserService;

@Configuration
public class SecurityConfig 
{

    private final UserService userService;

    public SecurityConfig(UserService userService) 
    {
        this.userService = userService;
    }

    // Define PasswordEncoder as a static bean to resolve circular dependency
    @Bean
    public static PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() 
    {
        return username -> userService.findUserByUsername(username)
            .map(user -> org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Encoded password
                .roles(user.getRole().toUpperCase())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception 
    {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService())
                   .passwordEncoder(passwordEncoder)
                   .and()
                   .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception 
    {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/user", "/login.html", "/register", "/css/**").permitAll()
                .requestMatchers("/hoursheet").hasRole("MANAGER")
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true)
                .failureUrl("/login.html?error=true"))
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html"));

        return http.build();
    }
}
