package com.coders.hospitalmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coders.hospitalmanagement.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

    // get bill by appointment
    Optional<Bill> findByAppointment_Id(Integer appointmentId);

    // patient bill history (via appointment)
    List<Bill> findByAppointment_Patient_User_Username(String username);
}
