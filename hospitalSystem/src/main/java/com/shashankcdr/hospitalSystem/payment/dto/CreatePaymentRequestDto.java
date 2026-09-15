package com.shashankcdr.hospitalSystem.payment.dto;

import com.shashankcdr.hospitalSystem.common.entity.type.PaymentMethod;
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

