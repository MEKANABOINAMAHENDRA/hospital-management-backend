package com.coders.hospitalmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coders.hospitalmanagement.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    // Get prescription by appointment (1 appointment = 1 prescription)
    Optional<Prescription> findByAppointment_Id(Integer appointmentId);

    // PATIENT: view own prescriptions
    List<Prescription> findByAppointment_Patient_User_Username(String username);

    // DOCTOR: view prescriptions written by doctor
    List<Prescription> findByAppointment_Doctor_User_Username(String username);
}
