package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.CreatePaymentRequestDto;
import com.shashankcdr.hospitalSystem.dto.PaymentResponseDto;
import com.shashankcdr.hospitalSystem.entity.Bill;
import com.shashankcdr.hospitalSystem.entity.Payment;
import com.shashankcdr.hospitalSystem.entity.type.PaymentStatus;
import com.shashankcdr.hospitalSystem.exception.ResourceNotFoundException;
import com.shashankcdr.hospitalSystem.repository.BillRepository;
import com.shashankcdr.hospitalSystem.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;

    @Transactional
    public PaymentResponseDto createPayment(
            CreatePaymentRequestDto request) {

        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Bill not found with id: " + request.getBillId()
                        )
                );

        // Prevent paying an already paid bill
        if (bill.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException(
                    "Bill is already paid"
            );
        }

        // Payment amount must match bill amount
        if (request.getAmount().compareTo(bill.getTotalAmount()) != 0) {
            throw new IllegalArgumentException(
                    "Payment amount must be equal to bill total amount"
            );
        }

        Payment payment = Payment.builder()
                .bill(bill)
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PAID)
                .transactionId(generateTransactionId())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        // Update bill payment status
        bill.setPaymentStatus(PaymentStatus.PAID);
        billRepository.save(bill);

        return mapToResponse(savedPayment);
    }

    public PaymentResponseDto getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + paymentId
                        )
                );

        return mapToResponse(payment);
    }

    public List<PaymentResponseDto> getPaymentsByBill(Long billId) {

        if (!billRepository.existsById(billId)) {
            throw new ResourceNotFoundException(
                    "Bill not found with id: " + billId
            );
        }

        return paymentRepository.findByBillId(billId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<PaymentResponseDto> getPaymentsByPatient(Long patientId) {

        return paymentRepository.findByBillPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID();
    }

    private PaymentResponseDto mapToResponse(Payment payment) {

        return PaymentResponseDto.builder()
                .id(payment.getId())
                .billId(payment.getBill().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}

