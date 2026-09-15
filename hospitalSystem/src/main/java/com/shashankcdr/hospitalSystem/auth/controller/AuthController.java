package com.shashankcdr.hospitalSystem.auth.controller;

import com.shashankcdr.hospitalSystem.auth.dto.LoginRequestDto;
import com.shashankcdr.hospitalSystem.auth.dto.LoginResponseDto;
import com.shashankcdr.hospitalSystem.auth.dto.SignupRequestDto;
import com.shashankcdr.hospitalSystem.auth.dto.SignupResponseDto;
import com.shashankcdr.hospitalSystem.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto>  signup(@RequestBody @Valid SignupRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}
