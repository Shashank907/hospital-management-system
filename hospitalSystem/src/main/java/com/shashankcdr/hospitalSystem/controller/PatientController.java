package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.dto.CreateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.dto.CreatePatientRequestDto;
import com.shashankcdr.hospitalSystem.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.service.AppointmentService;
import com.shashankcdr.hospitalSystem.service.PatientService;
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

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(
            @Valid @RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.createNewAppointment(createAppointmentRequestDto));
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