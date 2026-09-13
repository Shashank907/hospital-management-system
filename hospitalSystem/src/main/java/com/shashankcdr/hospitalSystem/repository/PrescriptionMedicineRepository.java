package com.shashankcdr.hospitalSystem.repository;

import com.shashankcdr.hospitalSystem.entity.PrescriptionMedicine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionMedicineRepository
        extends JpaRepository<PrescriptionMedicine, Long> {
}