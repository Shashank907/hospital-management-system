package com.shashankcdr.hospitalSystem.dto;

public record PrescriptionMedicineResponseDto(

        Long id,

        String medicineName,

        String dosage,

        String frequency,

        String duration,

        String instructions
) {
}