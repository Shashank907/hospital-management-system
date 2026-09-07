package com.shashankcdr.hospitalSystem;

import com.shashankcdr.hospitalSystem.entity.Patient;
import com.shashankcdr.hospitalSystem.entity.type.BloodGroupType;
import com.shashankcdr.hospitalSystem.repository.PatientRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class PatientTests {
    @Autowired
    private  PatientRepository patientRepository;
    @Test
    public void testPatientRepository(){
        List<Patient> patientList=patientRepository.findAll();
        System.out.println(patientList);
    }
    @Test
    public  void testTransactionalMethods(){
     List<Patient> patientList=patientRepository
             .findByBirthDateOrEmail(LocalDate.of(1988,3,15),"diya.patel@example.com");
     for(Patient patient:patientList){
         System.out.println(patient);
     }
     List<Patient> patientBloodGroupList=patientRepository.findByBloodGroup(BloodGroupType.A_POSITIVE);
        for(Patient patient:patientBloodGroupList){
            System.out.println(patient);
        }

        List<Object[]> bloodGroupList=patientRepository.countEachBloodGroupType();
        for(Object[] objects: bloodGroupList){
            System.out.println(objects[0]+" "+objects[1]);
        }
//        Page<Patient> patients=patientRepository.findAllPatients( PageRequest.of(0,2));
//        for(Patient patient:patients){
//            System.out.println(patient);
//        }
    }

}
