package com.shashankcdr.hospitalSystem.repository;

import com.shashankcdr.hospitalSystem.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    boolean existsByAppointmentId(Long appointmentId);

    List<Bill> findByPatientId(Long patientId);

    List<Bill> findByPatientUserUsername(String username);
}