package com.shashankcdr.hospitalSystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())

                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth


                                        .requestMatchers("/public/**", "/auth/**").permitAll()



//                                        // Admin + Doctor
//                                        .requestMatchers("/doctors/**")
//                                        .hasAnyRole("ADMIN", "DOCTOR")
//                                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                                        // Admin + Doctor
//                                        .requestMatchers("/appointments/**")
//                                        .hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "PATIENT")
//                                        // Admin + Doctor + Receptionist + Patient
//                                        .requestMatchers("/patients/**")
//                                        .hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST", "PATIENT")
                                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return httpSecurity.build();
    }
}
