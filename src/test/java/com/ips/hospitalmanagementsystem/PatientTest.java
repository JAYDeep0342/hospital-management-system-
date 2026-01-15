package com.ips.hospitalmanagementsystem;


import com.ips.hospitalmanagementsystem.dto.BloodGroupCountResponseEntity;
import com.ips.hospitalmanagementsystem.entity.Patient;
import com.ips.hospitalmanagementsystem.entity.type.Bloodgrouptype;
import com.ips.hospitalmanagementsystem.repository.PatientRepository;
import com.ips.hospitalmanagementsystem.service.PatientService;
import org.springframework.data.domain.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import java.util.List;


@SpringBootTest
public class PatientTest {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;
    private Patient patient;
    @Test

      public void testPatientRepository(){
//        List<Patient> patientList=patientRepository.findAll();
//        System.out.println((patientList));
   List<Patient> patientList = patientRepository.findAllPatientWithAppointment();
        System.out.println(patientList);
  }

    @Test
    public void testTransactionMethods() {
//        Patient patient =patientService.getPatient(1L);
//        Patient patient2 =patientRepository.findByName("Rahul Kumar");
//        System.out.println(patient2);
//        List< Patient> patient1 =patientRepository.findByBirthDateOrEmail(LocalDate.of(1992,8,20),"amit@example.com");
//        for (Patient patient: patient1){
//            System.out.println(patient1);
//        }
//List<Patient>patientlist=patientRepository.findByNameContaining("ri");
//        for (Patient patient: patientlist){
//            System.out.println(patientlist);
//        }
//        List<Patient> patientlist=patientRepository.findByBloodGroup(Bloodgrouptype.A_positive);
//
//        for (Patient patient: patientlist){
//            System.out.println(patientlist);
//        }
//                List<Patient> patientlist=patientRepository.findByBornAfterDate(LocalDate.of(1991,8,20));
//
//        for (Patient patient: patientlist){
//            System.out.println(patientlist);
//        }
//        List<Object[]>bloodgeouplist=patientRepository.countByEachBloodGroupType();
//        for (Object[] objects:bloodgeouplist){
//            System.out.println(objects[0]+""+ objects[1]);
//        }
        Page<Patient> patientList=patientRepository.findAllPatient(PageRequest.of(0,2));
       for (Patient patient :patientList){
           System.out.println(patient);
           }
//        int rowupdate = patientRepository.UpdateNameWithId("amit", 3L);
//        System.out.println(rowupdate);
//        List<BloodGroupCountResponseEntity> p1 =
//                patientRepository.countPatientsByBloodGroup();
//
//        for (BloodGroupCountResponseEntity b : p1) {
//            System.out.println(b);


        }
    }

