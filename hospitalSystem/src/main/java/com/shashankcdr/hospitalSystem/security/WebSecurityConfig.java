package com.shashankcdr.hospitalSystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrfConfig -> csrfConfig.disable())

                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
