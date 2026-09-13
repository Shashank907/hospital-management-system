package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.dto.CreateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.dto.UpdateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.service.AppointmentService;
import com.shashankcdr.hospitalSystem.dto.UpdateAppointmentStatusRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(
            @PathVariable Long appointmentId
    ) {

        return ResponseEntity.ok(
                appointmentService.getAppointmentById(appointmentId)
        );
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDto createAppointment(
            @RequestBody @Valid CreateAppointmentRequestDto request) {

        return appointmentService.createNewAppointmentByStaff(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST', 'DOCTOR')")
    @GetMapping
    public ResponseEntity<Page<AppointmentResponseDto>> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "appointmentTime") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                appointmentService.getAllAppointments(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody @Valid UpdateAppointmentRequestDto request
    ) {

        return ResponseEntity.ok(
                appointmentService.updateAppointment(
                        appointmentId,
                        request
                )
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    @PatchMapping("/{appointmentId}/status")
    public ResponseEntity<AppointmentResponseDto> updateAppointmentStatus(
            @PathVariable  Long appointmentId,
            @RequestBody @Valid UpdateAppointmentStatusRequestDto request
    ) {
        return ResponseEntity.ok(
                appointmentService.updateAppointmentStatus(
                        appointmentId,
                        request.getStatus()
                )
        );
    }
}