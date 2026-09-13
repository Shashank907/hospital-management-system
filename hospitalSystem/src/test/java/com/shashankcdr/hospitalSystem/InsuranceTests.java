package com.shashankcdr.hospitalSystem;

import com.shashankcdr.hospitalSystem.entity.Appointment;
import com.shashankcdr.hospitalSystem.entity.Insurance;
import com.shashankcdr.hospitalSystem.entity.Patient;
import com.shashankcdr.hospitalSystem.service.AppointmentService;
import com.shashankcdr.hospitalSystem.service.InsuranceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class InsuranceTests {
//    @Autowired
//    private InsuranceService insuranceService;
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Test
//    public  void testInsurance(){
//        Insurance insurance=Insurance.builder()
//                .policyNumber("HDFC_1234")
//                .provider("HDFC")
//                .validUntil(LocalDate.of(2030,12,12))
//                .build();
//       Patient patient= insuranceService.assignInsuranceToPatient(insurance,1L);
//
//var newPatient=insuranceService.disaccocaiteInsuranceFromPatient(patient.getId());
//        System.out.println(newPatient);
//    }
//
//

}
