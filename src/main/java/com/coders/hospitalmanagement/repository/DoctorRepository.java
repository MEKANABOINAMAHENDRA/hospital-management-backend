package com.coders.hospitalmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.coders.hospitalmanagement.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByUserUsername(String username);
}
