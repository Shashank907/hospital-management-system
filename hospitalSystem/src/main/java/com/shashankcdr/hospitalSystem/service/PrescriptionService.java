package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.CreatePrescriptionRequestDto;
import com.shashankcdr.hospitalSystem.dto.PrescriptionMedicineRequestDto;
import com.shashankcdr.hospitalSystem.dto.PrescriptionMedicineResponseDto;
import com.shashankcdr.hospitalSystem.dto.PrescriptionResponseDto;
import com.shashankcdr.hospitalSystem.entity.Appointment;
import com.shashankcdr.hospitalSystem.entity.Prescription;
import com.shashankcdr.hospitalSystem.entity.PrescriptionMedicine;
import com.shashankcdr.hospitalSystem.exception.ResourceAlreadyExistsException;
import com.shashankcdr.hospitalSystem.exception.ResourceNotFoundException;
import com.shashankcdr.hospitalSystem.repository.AppointmentRepository;
import com.shashankcdr.hospitalSystem.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    @Transactional
    public PrescriptionResponseDto createPrescription(
            CreatePrescriptionRequestDto request) {

        // 1. Find appointment
        Appointment appointment = appointmentRepository
                .findById(request.appointmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment not found"));

        // 2. Make sure appointment doesn't already have a prescription
        if (prescriptionRepository
                .existsByAppointmentId(request.appointmentId())) {

            throw new ResourceAlreadyExistsException(
                    "Prescription already exists for this appointment");
        }

        // 3. Get doctor and patient from appointment
        var doctor = appointment.getDoctor();
        var patient = appointment.getPatient();

        // 4. Create prescription
        Prescription prescription = Prescription.builder()
                .appointment(appointment)
                .doctor(doctor)
                .patient(patient)
                .diagnosis(request.diagnosis())
                .instructions(request.instructions())
                .build();

        // 5. Create medicines
        List<PrescriptionMedicine> medicines =
                request.medicines()
                        .stream()
                        .map(medicineRequest ->
                                createMedicine(
                                        medicineRequest,
                                        prescription
                                ))
                        .toList();

        // 6. Attach medicines to prescription
        prescription.setMedicines(medicines);

        // 7. Save prescription
        Prescription savedPrescription =
                prescriptionRepository.save(prescription);

        // 8. Convert entity to response DTO
        return mapToResponse(savedPrescription);
    }

    private PrescriptionMedicine createMedicine(
            PrescriptionMedicineRequestDto request,
            Prescription prescription) {

        return PrescriptionMedicine.builder()
                .prescription(prescription)
                .medicineName(request.medicineName())
                .dosage(request.dosage())
                .frequency(request.frequency())
                .duration(request.duration())
                .instructions(request.instructions())
                .build();
    }

    private PrescriptionResponseDto mapToResponse(
            Prescription prescription) {

        List<PrescriptionMedicineResponseDto> medicines =
                prescription.getMedicines()
                        .stream()
                        .map(medicine ->
                                new PrescriptionMedicineResponseDto(
                                        medicine.getId(),
                                        medicine.getMedicineName(),
                                        medicine.getDosage(),
                                        medicine.getFrequency(),
                                        medicine.getDuration(),
                                        medicine.getInstructions()
                                ))
                        .toList();

        return new PrescriptionResponseDto(
                prescription.getId(),
                prescription.getAppointment().getId(),
                prescription.getDoctor().getId(),
                prescription.getDoctor().getName(),
                prescription.getPatient().getId(),
                prescription.getPatient().getName(),
                prescription.getDiagnosis(),
                prescription.getInstructions(),
                prescription.getCreatedAt(),
                medicines
        );
    }
    @Transactional(readOnly = true)
    public PrescriptionResponseDto getPrescriptionById(Long prescriptionId) {

        Prescription prescription = prescriptionRepository
                .findById(prescriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Prescription not found"));

        return mapToResponse(prescription);
    }
    @Transactional(readOnly = true)
    public List<PrescriptionResponseDto> getPrescriptionsByPatient(Long patientId) {

        return prescriptionRepository.findByPatientId(patientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponseDto> getPrescriptionsByDoctor(Long doctorId) {

        return prescriptionRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}