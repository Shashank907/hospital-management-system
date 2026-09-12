package com.shashankcdr.hospitalSystem.dto;

import com.shashankcdr.hospitalSystem.entity.type.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequestDto {

    @NotNull
    private Long billId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal amount;

    @NotNull
    private PaymentMethod paymentMethod;
}

