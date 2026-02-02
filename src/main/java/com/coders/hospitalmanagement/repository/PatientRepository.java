package com.coders.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coders.hospitalmanagement.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByUserUsername(String username);
    Optional<Patient> findByPhoneno(String phoneno);


}
