package com.shashankcdr.hospitalSystem.security;

import com.shashankcdr.hospitalSystem.dto.LoginRequestDto;
import com.shashankcdr.hospitalSystem.dto.LoginResponseDto;
import com.shashankcdr.hospitalSystem.dto.SignupRequestDto;
import com.shashankcdr.hospitalSystem.dto.SignupResponseDto;
import com.shashankcdr.hospitalSystem.entity.User;
import com.shashankcdr.hospitalSystem.entity.type.Role;
import com.shashankcdr.hospitalSystem.repository.UserRepository;
import com.shashankcdr.hospitalSystem.exception.ResourceAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );

        User user= (User) authentication.getPrincipal();

        String token=authUtil.generateAccessToken(user);

        return new LoginResponseDto(token,
                user.getId(),
                user.getRole().name()
        );
    }

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user=userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user !=null) throw new ResourceAlreadyExistsException("User already exists");

        user=userRepository.save(User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(Role.PATIENT)
                .build()
        );

        return new SignupResponseDto(user.getId(),user.getUsername());

    }
}
