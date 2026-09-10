package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.CreatePatientRequestDto;
import com.shashankcdr.hospitalSystem.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.entity.Patient;
import com.shashankcdr.hospitalSystem.entity.User;
import com.shashankcdr.hospitalSystem.repository.PatientRepository;
import com.shashankcdr.hospitalSystem.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient Not " +
                "Found with id: " + patientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public PatientResponseDto createPatientProfile(
            CreatePatientRequestDto request,
            String username
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found: " + username
                        )
                );

        if (patientRepository.findByUserUsername(username).isPresent()) {
            throw new IllegalArgumentException(
                    "Patient profile already exists"
            );
        }

        Patient patient = new Patient();

        patient.setName(request.getName());
        patient.setBirthDate(request.getBirthDate());
        patient.setEmail(request.getEmail());
        patient.setGender(request.getGender());
        patient.setBloodGroup(request.getBloodGroup());

        // Automatically link Patient with authenticated User
        patient.setUser(user);

        Patient savedPatient = patientRepository.save(patient);

        return modelMapper.map(
                savedPatient,
                PatientResponseDto.class
        );
    }

    public  PatientResponseDto getPatientProfileByUsername(String username) {

        Patient patient = patientRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Patient not found for username: " + username
                        )
                );

        return modelMapper.map(patient, PatientResponseDto.class);
    }


    public PatientResponseDto getMyProfile(String username) {

        Patient patient = patientRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Patient profile not found"
                        )
                );

        return modelMapper.map(
                patient,
                PatientResponseDto.class
        );
    }

    public PatientResponseDto updateMyProfile(
            CreatePatientRequestDto request,
            String username
    ) {

        Patient patient = patientRepository
                .findByUserUsername(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Patient profile not found"
                        )
                );

        modelMapper.map(request, patient);

        Patient updatedPatient = patientRepository.save(patient);

        return modelMapper.map(
                updatedPatient,
                PatientResponseDto.class
        );
    }
}
