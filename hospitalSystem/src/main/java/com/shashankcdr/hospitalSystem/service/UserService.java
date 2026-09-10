package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.entity.User;
import com.shashankcdr.hospitalSystem.entity.type.Role;
import com.shashankcdr.hospitalSystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // your existing methods...


    public User changeRole(Long userId, Role newRole) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with ID: " + userId
                        )
                );

        user.setRole(newRole);

        return userRepository.save(user);
    }
}