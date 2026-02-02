package com.coders.hospitalmanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coders.hospitalmanagement.model.Appointment;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Integer> {

  
    // NURSE - ONLINE REQUESTS
 
    List<Appointment> findByStatus(String status);


    // TODAY APPOINTMENTS
  
    List<Appointment> findByAppointmentDate(LocalDate date);

    List<Appointment> findByAppointmentDateOrderBySerialNoAsc(
            LocalDate date);

    List<Appointment> findByAppointmentDateAndStatusInOrderBySerialNoAsc(
            LocalDate date,
            List<String> status
    );

   
    // SERIAL NUMBER CALCULATION
   
    @Query("""
        SELECT COUNT(a)
        FROM Appointment a
        WHERE a.doctor.did = :doctorId
        AND a.appointmentDate = :date
    """)
    int countTodayAppointments(
            @Param("doctorId") Integer doctorId,
            @Param("date") LocalDate date
    );

   
    // PATIENT DASHBOARD
  
    List<Appointment> findByPatient_User_Username(String username);
    
    // DOCTOR DASHBOARD
   
    List<Appointment> findByDoctor_User_Username(String username);

    List<Appointment> findByDoctor_User_UsernameAndStatus(
            String username, String status
    );

    List<Appointment> findByDoctor_User_UsernameAndStatusIn(
            String username,
            List<String> status
    );

    // OPTIONAL
    
    Optional<Appointment> findTopByPatient_IdOrderByIdDesc(
            Integer patientId
    );
}
