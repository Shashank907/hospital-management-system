package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final AppointmentService appointmentService;

    @GetMapping("/{doctorId}/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                appointmentService.getAllAppointmentsOfDoctor(doctorId)
        );
    }
}





