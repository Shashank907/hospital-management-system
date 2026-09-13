package com.shashankcdr.hospitalSystem.controller;

import com.shashankcdr.hospitalSystem.dto.BillResponseDto;
import com.shashankcdr.hospitalSystem.dto.CreateBillRequestDto;
import com.shashankcdr.hospitalSystem.entity.type.PaymentStatus;
import com.shashankcdr.hospitalSystem.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping
    public ResponseEntity<BillResponseDto> createBill(
            @RequestBody @Valid CreateBillRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(billService.createBill(request));
    }

    @GetMapping("/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<BillResponseDto> getBillById(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                billService.getBillById(billId)
        );
    }
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<List<BillResponseDto>> getBillsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                billService.getBillsByPatient(patientId)
        );
    }


}