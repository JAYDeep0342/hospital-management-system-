package com.ips.hospitalmanagementsystem.service;
import com.ips.hospitalmanagementsystem.dto.AppointmentResponseDto;
import com.ips.hospitalmanagementsystem.dto.CreateAppointmentRequestDto;
import com.ips.hospitalmanagementsystem.entity.Appointment;
import com.ips.hospitalmanagementsystem.entity.Doctor;
import com.ips.hospitalmanagementsystem.entity.Patient;
import com.ips.hospitalmanagementsystem.repository.AppointmentRepository;
import com.ips.hospitalmanagementsystem.repository.DoctorRepository;
import com.ips.hospitalmanagementsystem.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AppointmentService {
    private final PatientRepository patientRepository;
    private final  AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto createAppointmentRequestDto) {
        Long doctorId = createAppointmentRequestDto.getDoctorId();
        Long patientId = createAppointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Appointment appointment = Appointment.builder()
                .reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); // this will automatically call the update, because it is dirty

        doctor.getAppointments().add(appointment); // just for bidirectional consistency

        return appointment;
    }
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return doctor.getAppointments()
                .stream()
                .map(appointment ->
                        modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}
