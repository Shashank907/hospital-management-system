package com.shashankcdr.hospitalSystem.payment.controller;

import com.shashankcdr.hospitalSystem.payment.dto.CreatePaymentRequestDto;
import com.shashankcdr.hospitalSystem.payment.dto.PaymentResponseDto;
import com.shashankcdr.hospitalSystem.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    public ResponseEntity<PaymentResponseDto> createPayment(
            @RequestBody @Valid CreatePaymentRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<PaymentResponseDto> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId)
        );
    }

    @GetMapping("/bill/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByBill(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByBill(billId)
        );
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByPatient(patientId)
        );
    }
}

