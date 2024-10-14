package com.gcu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register", "/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("/hoursheets").hasRole("MANAGER") // Only manager can access hoursheets
                .anyRequest().authenticated() // All other requests require authentication
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // Custom login page
                .successHandler(roleBasedAuthenticationSuccessHandler()) // Use the custom success handler
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .httpBasic(); // Enable basic authentication for testing

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler roleBasedAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    org.springframework.security.core.Authentication authentication)
                    throws IOException, ServletException {

                // Print roles for debugging
                System.out.println("User roles: " + authentication.getAuthorities());

                // Redirect based on roles
                String role = authentication.getAuthorities().toString();
                if (role.contains("ROLE_MANAGER")) {
                    System.out.println("Redirecting to /hoursheets");
                    response.sendRedirect("/hoursheets");
                } else {
                    System.out.println("Redirecting to /home");
                    response.sendRedirect("/home");
                }
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails manager = User.builder()
            .username("manager")
            .password(passwordEncoder.encode("manager123"))
            .roles("MANAGER")
            .build();

        UserDetails employee = User.builder()
            .username("employee")
            .password(passwordEncoder.encode("employee123"))
            .roles("EMPLOYEE")
            .build();

        return new InMemoryUserDetailsManager(manager, employee);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
