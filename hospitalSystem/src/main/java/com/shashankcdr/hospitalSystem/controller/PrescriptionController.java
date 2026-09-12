package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.CreatePrescriptionRequestDto;
import com.shashankcdr.hospitalSystem.dto.PrescriptionResponseDto;
import com.shashankcdr.hospitalSystem.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<PrescriptionResponseDto> createPrescription(
            @RequestBody @Valid CreatePrescriptionRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(prescriptionService.createPrescription(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST', 'PATIENT')")
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<PrescriptionResponseDto> getPrescriptionById(
            @PathVariable Long prescriptionId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionById(prescriptionId)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<PrescriptionResponseDto>> getPrescriptionsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByPatient(patientId)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<PrescriptionResponseDto>> getPrescriptionsByDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                prescriptionService.getPrescriptionsByDoctor(doctorId)
        );
    }
}