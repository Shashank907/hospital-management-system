package com.shashankcdr.hospitalSystem.common.dto;

import com.shashankcdr.hospitalSystem.common.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloodGroupCountResponseEntity {
    private BloodGroupType bloodGroup;
    private Long count;
}
