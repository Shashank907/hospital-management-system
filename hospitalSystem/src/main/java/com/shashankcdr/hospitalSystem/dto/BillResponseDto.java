
package com.shashankcdr.hospitalSystem.dto;

import com.shashankcdr.hospitalSystem.entity.type.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BillResponseDto(
        Long id,
        Long appointmentId,
        Long patientId,
        String patientName,
        BigDecimal consultationFee,
        BigDecimal medicineCharges,
        BigDecimal otherCharges,
        BigDecimal totalAmount,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt
) {
}