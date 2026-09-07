package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.DoctorResponseDto;
import com.shashankcdr.hospitalSystem.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.service.DoctorService;
import com.shashankcdr.hospitalSystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));
    }
    @GetMapping("/doctors") public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok( doctorService.getAllDoctors() );
    }
}