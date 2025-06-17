package io.github.larrythexu.SecureDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // "/", "/home", "/login", "/logout" - permitAll() means they can be accessed w/o auth

        http
            .authorizeHttpRequests(
                (requests) -> requests
                    .requestMatchers("/", "/home").permitAll() // Match paths for no auth
                    .anyRequest().authenticated() // Anything else must require authentication
                )
            .formLogin(
                (form) -> form
                    .loginPage("/login") // Assign default login page - accessing protected pages redirect here
                    .permitAll()
                )
            .logout(
                (logout) -> logout.permitAll());

            return http.build();

    }

    // For demo purposes, setting up an in-memory user
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                                .username("user")
                                .password("password")
                                .roles("USER")
                                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
