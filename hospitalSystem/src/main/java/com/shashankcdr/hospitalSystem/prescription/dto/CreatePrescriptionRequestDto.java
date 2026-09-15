package com.shashankcdr.hospitalSystem.prescription.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreatePrescriptionRequestDto(

        @NotNull
        Long appointmentId,

        @NotBlank
        String diagnosis,

        String instructions,

        @NotEmpty
        List<@Valid PrescriptionMedicineRequestDto> medicines
) {
}