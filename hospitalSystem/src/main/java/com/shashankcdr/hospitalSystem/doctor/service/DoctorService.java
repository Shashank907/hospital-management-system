package com.shashankcdr.hospitalSystem.doctor.service;

import com.shashankcdr.hospitalSystem.doctor.dto.DoctorResponseDto;
import com.shashankcdr.hospitalSystem.doctor.repository.DoctorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .toList();
    }


}
