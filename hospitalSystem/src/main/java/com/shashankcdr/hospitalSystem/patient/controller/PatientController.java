package com.shashankcdr.hospitalSystem.patient.controller;

import com.shashankcdr.hospitalSystem.appointment.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.appointment.service.AppointmentService;
import com.shashankcdr.hospitalSystem.patient.dto.CreatePatientAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.patient.dto.CreatePatientRequestDto;
import com.shashankcdr.hospitalSystem.patient.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/me/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(
            @Valid @RequestBody CreatePatientAppointmentRequestDto request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.createNewAppointment(
                        request,
                        username
                ));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping("/profile")
    public ResponseEntity<PatientResponseDto> createPatientProfile(
            @Valid @RequestBody CreatePatientRequestDto request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(patientService.createPatientProfile(
                        request,
                        username
                ));
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/me")
    public ResponseEntity<PatientResponseDto> getMyProfile(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                patientService.getMyProfile(username)
        );
    }

    @PreAuthorize("hasRole('PATIENT')")
    @PutMapping("/me")
    public ResponseEntity<PatientResponseDto> updateMyProfile(
            @Valid @RequestBody CreatePatientRequestDto request,
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                patientService.updateMyProfile(
                        request,
                        username
                )
        );
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/me/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getMyAppointments(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                appointmentService.getMyAppointments(username)
        );
    }
}