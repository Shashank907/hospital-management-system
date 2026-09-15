package com.shashankcdr.hospitalSystem.prescription.dto;

import jakarta.validation.constraints.NotBlank;

public record PrescriptionMedicineRequestDto(

        @NotBlank
        String medicineName,

        @NotBlank
        String dosage,

        @NotBlank
        String frequency,

        @NotBlank
        String duration,

        String instructions
) {
}