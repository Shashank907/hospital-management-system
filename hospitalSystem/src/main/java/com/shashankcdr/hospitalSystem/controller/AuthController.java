package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.LoginRequestDto;
import com.shashankcdr.hospitalSystem.dto.LoginResponseDto;
import com.shashankcdr.hospitalSystem.dto.SignupRequestDto;
import com.shashankcdr.hospitalSystem.dto.SignupResponseDto;
import com.shashankcdr.hospitalSystem.security.AuthService;
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
