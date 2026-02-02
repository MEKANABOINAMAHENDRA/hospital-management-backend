package com.coders.hospitalmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coders.hospitalmanagement.model.PrescriptionItem;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Integer> {

    // Get all medicines for a prescription
    List<PrescriptionItem> findByPrescription_Id(Integer prescriptionId);
}

