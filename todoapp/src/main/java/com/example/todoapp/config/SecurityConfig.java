package com.example.todoapp.config;

import com.example.todoapp.model.User;
import com.example.todoapp.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

@Bean
public UserDetailsService userDetailsService() {
    return username -> {
        User user = userService.findByUsername(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    };
}


    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            // Public static resources and auth pages
            .requestMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/signup",
                "/register",
                "/login"
            ).permitAll()

            // Allow modal edit form fetch without login (for dynamic modal loading)
            .requestMatchers("/dashboard/edit-form/**").permitAll()

            // Require authentication for all dashboard routes
            .requestMatchers("/dashboard/**").authenticated()

            // Any other request requires authentication
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/dashboard", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
        )
        // For development; consider enabling in production with CSRF tokens in forms
        .csrf().disable();

    return http.build();
}

}
