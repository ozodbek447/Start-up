package com.example.testwebb.data;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/admin",
                                "/register",
                                "/css/**",
                                "/js/**"
                        ).permitAll()   // login shart emas
                        .anyRequest().authenticated() // qolganlari login
                )
                .oauth2Login(oauth -> oauth
                        .defaultSuccessUrl("/kabinet", true)
                );

        return http.build();
    }
}
