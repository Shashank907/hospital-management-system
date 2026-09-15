package com.shashankcdr.hospitalSystem.appointment.dto;

import com.shashankcdr.hospitalSystem.common.entity.type.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAppointmentStatusRequestDto {
    @NotNull(message = "Appointment status is required")
    private AppointmentStatus status;

}
