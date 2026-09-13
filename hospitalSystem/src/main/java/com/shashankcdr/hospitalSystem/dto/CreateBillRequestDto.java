package com.shashankcdr.hospitalSystem.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateBillRequestDto(

        @NotNull
        Long appointmentId,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal consultationFee,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal medicineCharges,

        @NotNull
        @DecimalMin("0.00")
        BigDecimal otherCharges
) {
}