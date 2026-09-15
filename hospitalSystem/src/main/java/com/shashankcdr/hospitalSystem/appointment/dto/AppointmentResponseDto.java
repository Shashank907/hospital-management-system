package com.shashankcdr.hospitalSystem.appointment.dto;

import com.shashankcdr.hospitalSystem.doctor.dto.DoctorResponseDto;
import com.shashankcdr.hospitalSystem.common.entity.type.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponseDto {
    private Long id;

    private LocalDateTime appointmentTime;

    private String reason;

    private DoctorResponseDto doctor;

    private AppointmentStatus status;
}
