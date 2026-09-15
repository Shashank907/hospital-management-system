package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.AppointmentResponseDto;
import com.shashankcdr.hospitalSystem.dto.CreateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.dto.CreatePatientAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.dto.UpdateAppointmentRequestDto;
import com.shashankcdr.hospitalSystem.entity.Appointment;
import com.shashankcdr.hospitalSystem.entity.Doctor;
import com.shashankcdr.hospitalSystem.entity.Patient;
import com.shashankcdr.hospitalSystem.entity.type.AppointmentStatus;
import com.shashankcdr.hospitalSystem.exception.ResourceNotFoundException;
import com.shashankcdr.hospitalSystem.repository.AppointmentRepository;
import com.shashankcdr.hospitalSystem.repository.DoctorRepository;
import com.shashankcdr.hospitalSystem.repository.PatientRepository;
import org.springframework.transaction.annotation.Transactional;
import com.shashankcdr.hospitalSystem.exception.ConflictException;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public AppointmentResponseDto createNewAppointment(
            CreatePatientAppointmentRequestDto request,
            String username
    ) {

        Patient patient = patientRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient profile not found for user: " + username
                        )
                );

            Doctor doctor = doctorRepository
                    .findById(request.getDoctorId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Doctor not found with ID: " + request.getDoctorId()
                            )
                    );

            Appointment appointment = Appointment.builder()
                    .reason(request.getReason())
                    .appointmentTime(request.getAppointmentTime())
                    .status(AppointmentStatus.SCHEDULED)
                    .patient(patient)
                    .doctor(doctor)
                    .build();

            patient.getAppointments().add(appointment);
            doctor.getAppointments().add(appointment);

            Appointment savedAppointment =
                    appointmentRepository.save(appointment);

            return modelMapper.map(
                    savedAppointment,
                    AppointmentResponseDto.class
            );
        }


    @Transactional
    public AppointmentResponseDto createNewAppointmentByStaff(
            CreateAppointmentRequestDto request
    ) {

        Patient patient = patientRepository
                .findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Patient not found with ID: " + request.getPatientId()
                        )
                );

        Doctor doctor = doctorRepository
                .findById(request.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with ID: " + request.getDoctorId()
                        )
                );
        if (appointmentRepository.existsByDoctorIdAndAppointmentTime(
                request.getDoctorId(),
                request.getAppointmentTime())) {

            throw new ConflictException(
                    "Doctor already has an appointment at this time"
            );
        }
        Appointment appointment = Appointment.builder()
                .reason(request.getReason())
                .appointmentTime(request.getAppointmentTime())
                .status(AppointmentStatus.SCHEDULED)
                .patient(patient)
                .doctor(doctor)
                .build();

        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        return modelMapper.map(
                savedAppointment,
                AppointmentResponseDto.class
        );
    }



    @Transactional
        public AppointmentResponseDto  reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
            Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
                    () ->
                            new ResourceNotFoundException(
                                    "Appointment not found with ID: " + appointmentId
                            )
            );
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Doctor not found with ID: " + doctorId
                    ));

        // Prevent double-booking
        if (appointmentRepository.existsByDoctorIdAndAppointmentTimeAndIdNot(
                doctorId,
                appointment.getAppointmentTime(),
                appointmentId
        )) {
            throw new ConflictException(
                    "Doctor already has an appointment at this time"
            );
        }
            appointment.setDoctor(doctor); // this will automatically call the update, because it is dirty

            doctor.getAppointments().add(appointment); // just for bidirectional consistency

            return modelMapper.map(
                    appointment, AppointmentResponseDto.class
            );
        }

        @Transactional(readOnly = true)
        public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
            Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->
                    new ResourceNotFoundException(
                            "Doctor not found with ID: " + doctorId
                    ));

            return doctor.getAppointments()
                    .stream()
                    .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                    .collect(Collectors.toList());
        }

        @Transactional(readOnly = true)
    public List<AppointmentResponseDto> getMyAppointments(String username) {

        Patient patient = patientRepository.findByUserUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found")
                );

        List<Appointment> appointments =
                appointmentRepository.findByPatientIdWithDoctor(patient.getId());

        return appointments.stream()
                .map(appointment ->
                        modelMapper.map(
                                appointment,
                                AppointmentResponseDto.class
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public AppointmentResponseDto getAppointmentById(Long appointmentId) {

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with ID: " + appointmentId
                        )
                );

        return modelMapper.map(
                appointment,
                AppointmentResponseDto.class
        );
    }



    @Transactional(readOnly = true)
    public Page<AppointmentResponseDto> getAllAppointments(
            int pageNumber,
            int pageSize,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Page<Appointment> appointments =
                appointmentRepository.findAll(
                        PageRequest.of(pageNumber, pageSize, sort)
                );

        return appointments.map(
                appointment ->
                        modelMapper.map(
                                appointment,
                                AppointmentResponseDto.class
                        )
        );
    }


    @Transactional
    public AppointmentResponseDto updateAppointment(
            Long appointmentId,
            UpdateAppointmentRequestDto request
    ) {

        Appointment appointment = appointmentRepository
                .findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with ID: " + appointmentId
                        )
                );

        Doctor doctor = doctorRepository
                .findById(request.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with ID: " + request.getDoctorId()
                        )
                );

        // Prevent double-booking
        if (appointmentRepository.existsByDoctorIdAndAppointmentTimeAndIdNot(
                request.getDoctorId(),
                request.getAppointmentTime(),
                appointmentId
        )) {
            throw new ConflictException(
                    "Doctor already has an appointment at this time"
            );
        }

        appointment.setDoctor(doctor);
        appointment.setReason(request.getReason());
        appointment.setAppointmentTime(request.getAppointmentTime());

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return modelMapper.map(
                updatedAppointment,
                AppointmentResponseDto.class
        );
    }


    @Transactional
    public AppointmentResponseDto updateAppointmentStatus(
            Long appointmentId,
            AppointmentStatus newStatus
    ) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Appointment not found with ID: " + appointmentId
                        )
                );

        AppointmentStatus currentStatus = appointment.getStatus();

        boolean validTransition =
                (currentStatus == AppointmentStatus.SCHEDULED &&
                        (newStatus == AppointmentStatus.CONFIRMED ||
                                newStatus == AppointmentStatus.CANCELLED))

                        ||

                        (currentStatus == AppointmentStatus.CONFIRMED &&
                                (newStatus == AppointmentStatus.COMPLETED ||
                                        newStatus == AppointmentStatus.CANCELLED));

        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid appointment status transition: "
                            + currentStatus + " -> " + newStatus
            );
        }

        appointment.setStatus(newStatus);

        Appointment updatedAppointment =
                appointmentRepository.save(appointment);

        return modelMapper.map(
                updatedAppointment,
                AppointmentResponseDto.class
        );
    }
}
