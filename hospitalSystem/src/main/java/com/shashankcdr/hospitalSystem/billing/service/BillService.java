
package com.shashankcdr.hospitalSystem.billing.service;

import com.shashankcdr.hospitalSystem.billing.dto.BillResponseDto;
import com.shashankcdr.hospitalSystem.billing.dto.CreateBillRequestDto;
import com.shashankcdr.hospitalSystem.billing.entity.Bill;
import com.shashankcdr.hospitalSystem.common.entity.type.PaymentStatus;
import com.shashankcdr.hospitalSystem.common.exception.ResourceAlreadyExistsException;
import com.shashankcdr.hospitalSystem.common.exception.ResourceNotFoundException;
import com.shashankcdr.hospitalSystem.appointment.repository.AppointmentRepository;
import com.shashankcdr.hospitalSystem.billing.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public BillResponseDto createBill(CreateBillRequestDto request) {

        var appointment = appointmentRepository
                .findById(request.appointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        if (billRepository.existsByAppointmentId(request.appointmentId())) {
            throw new ResourceAlreadyExistsException(
                    "Bill already exists for this appointment");
        }

        BigDecimal totalAmount =
                request.consultationFee()
                        .add(request.medicineCharges())
                        .add(request.otherCharges());

        Bill bill = Bill.builder()
                .appointment(appointment)
                .patient(appointment.getPatient())
                .consultationFee(request.consultationFee())
                .medicineCharges(request.medicineCharges())
                .otherCharges(request.otherCharges())
                .totalAmount(totalAmount)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        Bill savedBill = billRepository.save(bill);

        return mapToResponse(savedBill);
    }

    @Transactional(readOnly = true)
    public BillResponseDto getBillById(Long billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));

        return mapToResponse(bill);
    }

    @Transactional(readOnly = true)
    public List<BillResponseDto> getBillsByPatient(Long patientId) {

        return billRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public BillResponseDto updatePaymentStatus(
            Long billId,
            PaymentStatus paymentStatus) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bill not found"));

        bill.setPaymentStatus(paymentStatus);

        Bill updatedBill = billRepository.save(bill);

        return mapToResponse(updatedBill);
    }

    @Transactional(readOnly = true)
    public List<BillResponseDto> getMyBills(String username) {

        return billRepository.findByPatientUserUsername(username)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private BillResponseDto mapToResponse(Bill bill) {

        return new BillResponseDto(
                bill.getId(),
                bill.getAppointment().getId(),
                bill.getPatient().getId(),
                bill.getPatient().getName(),
                bill.getConsultationFee(),
                bill.getMedicineCharges(),
                bill.getOtherCharges(),
                bill.getTotalAmount(),
                bill.getPaymentStatus(),
                bill.getCreatedAt()
        );
    }
}


