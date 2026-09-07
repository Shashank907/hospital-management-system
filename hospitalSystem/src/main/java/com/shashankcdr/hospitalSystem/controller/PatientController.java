package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.dto.CreateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.service.AppointmentService;
import com.shashankcdr.hospitalSystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(@RequestBody CreateAppointmentRequestDto createAppointmentRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile(Authentication authentication){
            String username = authentication.getName();

            return ResponseEntity.ok(patientService.getPatientProfileByUsername(username));
        }

}
