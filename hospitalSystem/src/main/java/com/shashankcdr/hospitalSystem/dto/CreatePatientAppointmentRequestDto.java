package com.shashankcdr.hospitalSystem.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreatePatientAppointmentRequestDto {

    @NotNull
    private Long doctorId;

    @NotNull
    @Future
    private LocalDateTime appointmentTime;

    @Size(max = 500)
    private String reason;
}

