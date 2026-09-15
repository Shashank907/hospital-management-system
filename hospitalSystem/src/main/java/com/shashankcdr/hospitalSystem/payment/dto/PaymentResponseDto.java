package com.shashankcdr.hospitalSystem.payment.dto;

import com.shashankcdr.hospitalSystem.common.entity.type.PaymentMethod;
import com.shashankcdr.hospitalSystem.common.entity.type.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponseDto {

    private Long id;

    private Long billId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDateTime paidAt;
}

