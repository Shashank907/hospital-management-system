package com.shashankcdr.hospitalSystem.security;

import com.shashankcdr.hospitalSystem.entity.User;
import com.shashankcdr.hospitalSystem.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");

        if (requestTokenHeader == null ||
                !requestTokenHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = requestTokenHeader.substring(7);

        try {

            String username = authUtil.getUsernameFromToken(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                User user = userRepository
                        .findByUsername(username)
                        .orElse(null);

                if (user != null) {
                    System.out.println("USERNAME: " + user.getUsername());
                    System.out.println("ROLE: " + user.getRole());
                    System.out.println("AUTHORITIES: " + user.getAuthorities());
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }

        } catch (JwtException | IllegalArgumentException e) {

            log.warn("Invalid JWT token");

        }
        System.out.println(
                "AUTHENTICATED USER: " +
                        SecurityContextHolder.getContext().getAuthentication()
        );
        filterChain.doFilter(request, response);
    }
}