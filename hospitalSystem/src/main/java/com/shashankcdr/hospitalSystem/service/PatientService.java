package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.PatientResponseDto;
import com.shashankcdr.hospitalSystem.entity.Patient;
import com.shashankcdr.hospitalSystem.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
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
}
