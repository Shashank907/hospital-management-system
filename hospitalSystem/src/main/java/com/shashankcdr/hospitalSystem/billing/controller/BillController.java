package com.shashankcdr.hospitalSystem.billing.controller;

import com.shashankcdr.hospitalSystem.billing.dto.BillResponseDto;
import com.shashankcdr.hospitalSystem.billing.dto.CreateBillRequestDto;
import com.shashankcdr.hospitalSystem.billing.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    // ADMIN / RECEPTIONIST
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTIONIST')")
    @PostMapping
    public ResponseEntity<BillResponseDto> createBill(
            @RequestBody @Valid CreateBillRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(billService.createBill(request));
    }

    // ADMIN / DOCTOR / RECEPTIONIST
    @GetMapping("/{billId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<BillResponseDto> getBillById(
            @PathVariable Long billId) {

        return ResponseEntity.ok(
                billService.getBillById(billId)
        );
    }

    // ADMIN / DOCTOR / RECEPTIONIST
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'RECEPTIONIST')")
    public ResponseEntity<List<BillResponseDto>> getBillsByPatient(
            @PathVariable Long patientId) {

        return ResponseEntity.ok(
                billService.getBillsByPatient(patientId)
        );
    }

    // PATIENT - own bills
    @GetMapping("/me")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<BillResponseDto>> getMyBills(
            Authentication authentication) {

        String username = authentication.getName();

        return ResponseEntity.ok(
                billService.getMyBills(username)
        );
    }
}
