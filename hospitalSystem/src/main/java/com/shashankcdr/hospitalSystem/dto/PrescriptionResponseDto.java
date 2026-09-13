package com.shashankcdr.hospitalSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PrescriptionResponseDto(

        Long id,

        Long appointmentId,

        Long doctorId,

        String doctorName,

        Long patientId,

        String patientName,

        String diagnosis,

        String instructions,

        LocalDateTime createdAt,

        List<PrescriptionMedicineResponseDto> medicines
) {
}