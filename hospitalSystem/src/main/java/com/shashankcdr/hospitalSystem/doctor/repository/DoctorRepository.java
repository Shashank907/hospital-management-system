package com.shashankcdr.hospitalSystem.doctor.repository;

import com.shashankcdr.hospitalSystem.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
