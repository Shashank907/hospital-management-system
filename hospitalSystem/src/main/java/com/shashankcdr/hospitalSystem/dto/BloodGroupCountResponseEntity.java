package com.shashankcdr.hospitalSystem.dto;

import com.shashankcdr.hospitalSystem.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloodGroupCountResponseEntity {
    private BloodGroupType bloodGroup;
    private Long count;
}
