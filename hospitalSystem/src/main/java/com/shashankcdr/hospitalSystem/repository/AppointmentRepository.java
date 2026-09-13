package com.shashankcdr.hospitalSystem.repository;

import com.shashankcdr.hospitalSystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByPatientId(Long patientId);
    @Query("""
        SELECT a
        FROM Appointment a
        JOIN FETCH a.doctor
        WHERE a.patient.id = :patientId
        """)
    List<Appointment> findByPatientIdWithDoctor(@Param("patientId") Long patientId);
}
