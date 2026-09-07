package com.shashankcdr.hospitalSystem.repository;

import com.shashankcdr.hospitalSystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
