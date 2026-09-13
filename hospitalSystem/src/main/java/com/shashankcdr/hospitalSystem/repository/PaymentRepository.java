package com.shashankcdr.hospitalSystem.repository;

import com.shashankcdr.hospitalSystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBillId(Long billId);

    List<Payment> findByBillPatientId(Long patientId);
}

