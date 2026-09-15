package com.shashankcdr.hospitalSystem.prescription.repository;

import com.shashankcdr.hospitalSystem.prescription.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionMedicineRepository
        extends JpaRepository<PrescriptionMedicine, Long> {
}