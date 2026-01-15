package com.ips.hospitalmanagementsystem.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
;

import com.ips.hospitalmanagementsystem.dto.BloodGroupCountResponseEntity;
import com.ips.hospitalmanagementsystem.entity.Patient;
import com.ips.hospitalmanagementsystem.entity.type.Bloodgrouptype;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

     Patient findByName(String name);
    List< Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);
    List< Patient> findByNameContaining(String query);
    @Query("select p from Patient p where p.bloodGroup=?1")
    List<Patient>findByBloodGroup(@Param("bloodGroup") Bloodgrouptype bloodGroup);
    @Query("select p from Patient p where p.birthDate >:birthDate")
    List<Patient>findByBornAfterDate(@Param("birthDate") LocalDate birthDate);
    @Query("select p.bloodGroup ,Count(p) from Patient p group by p.bloodGroup")
    List<Object[]> countByEachBloodGroupType();
//    @Query(value = "select * from patient100",nativeQuery = true)
//    List<Patient>findAllPatient();

    @Transactional
    @Modifying
    @Query( "update Patient p set p.name =:name where p.id = :id ")
    int UpdateNameWithId(@Param("name") String name,@Param("id") Long id );

    @Query("SELECT new com.ips.hospitalmanagementsystem.dto.BloodGroupCountResponseEntity(" +
            "p.bloodGroup, COUNT(p)) " +
            "FROM Patient p GROUP BY p.bloodGroup")
    List<BloodGroupCountResponseEntity> countPatientsByBloodGroup();

    @Query(value = "select * from patient100", nativeQuery = true)
    Page<Patient> findAllPatients(Pageable pageable);
    //    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments a LEFT JOIN FETCH a.doctor")
    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.appointments")
    List<Patient> findAllPatientWithAppointment();








}
