package com.shashankcdr.hospitalSystem.service;

import com.shashankcdr.hospitalSystem.dto.DoctorResponseDto;
import com.shashankcdr.hospitalSystem.repository.DoctorRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
