package com.shashankcdr.hospitalSystem.dto;

import com.shashankcdr.hospitalSystem.entity.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CreateAppointmentRequestDto {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private String reason;
}
