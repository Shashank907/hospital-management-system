package com.shashankcdr.hospitalSystem.prescription.dto;

public record PrescriptionMedicineResponseDto(

        Long id,

        String medicineName,

        String dosage,

        String frequency,

        String duration,

        String instructions
) {
}