package com.shashankcdr.hospitalSystem.user.dto;

import com.shashankcdr.hospitalSystem.user.entity.Role;
import lombok.Data;

@Data
public class ChangeRoleRequestDto {
    private Role role;
}