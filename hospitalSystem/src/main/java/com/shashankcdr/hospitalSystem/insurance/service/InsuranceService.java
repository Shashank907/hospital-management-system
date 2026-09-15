package com.shashankcdr.hospitalSystem.insurance.service;

import com.shashankcdr.hospitalSystem.insurance.entity.Insurance;
import com.shashankcdr.hospitalSystem.patient.entity.Patient;
import com.shashankcdr.hospitalSystem.common.exception.ResourceNotFoundException;
import com.shashankcdr.hospitalSystem.insurance.repository.InsuranceRepository;
import com.shashankcdr.hospitalSystem.patient.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient); // bidirectional consistency maintainence

        return patient;
    }

    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(null);
        return patient;
    }
}
