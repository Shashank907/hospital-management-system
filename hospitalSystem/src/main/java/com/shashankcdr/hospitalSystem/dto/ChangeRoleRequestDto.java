package com.shashankcdr.hospitalSystem.dto;

import com.shashankcdr.hospitalSystem.entity.type.Role;
import lombok.Data;

@Data
public class ChangeRoleRequestDto {
    private Role role;
}