package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.ChangeRoleRequestDto;
import com.shashankcdr.hospitalSystem.entity.User;
import com.shashankcdr.hospitalSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

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