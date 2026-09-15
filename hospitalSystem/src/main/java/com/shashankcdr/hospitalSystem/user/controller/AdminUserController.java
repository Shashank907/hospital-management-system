package com.shashankcdr.hospitalSystem.user.controller;

import com.shashankcdr.hospitalSystem.user.dto.ChangeRoleRequestDto;
import com.shashankcdr.hospitalSystem.user.entity.User;
import com.shashankcdr.hospitalSystem.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/role")
    public User changeRole(
            @PathVariable Long userId,
            @RequestBody ChangeRoleRequestDto request) {

        return userService.changeRole(
                userId,
                request.getRole()
        );
    }
}