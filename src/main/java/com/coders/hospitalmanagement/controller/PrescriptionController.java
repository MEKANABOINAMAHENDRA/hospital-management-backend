package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.dto.PrescriptionRequestDTO;
import com.coders.hospitalmanagement.dto.PrescriptionResponseDTO;
import com.coders.hospitalmanagement.service.PrescriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService service;

    // DOCTOR: Create prescription
    @PostMapping("/{appointmentId}")
    public PrescriptionResponseDTO createPrescription(
            @PathVariable int appointmentId,
            @Valid @RequestBody PrescriptionRequestDTO dto,
            Authentication auth) {

        return service.createPrescription(
                appointmentId,
                dto,
                auth.getName()
        );
    }

    // PATIENT: View own prescriptions
    @GetMapping("/my")
    public List<PrescriptionResponseDTO> myPrescriptions(Authentication auth) {

        return service.getPrescriptionsForPatient(auth.getName());
    }

    // DOCTOR: View prescriptions written by him
    @GetMapping("/doctor/my")
    public List<PrescriptionResponseDTO> doctorPrescriptions(Authentication auth) {

        return service.getPrescriptionsForDoctor(auth.getName());
    }

    // ADMIN: View all prescriptions
    @GetMapping
    public List<PrescriptionResponseDTO> allPrescriptions() {

        return service.getAllPrescriptions();
    }

    // View by appointment
    @GetMapping("/appointment/{appointmentId}")
    public PrescriptionResponseDTO getByAppointment(
            @PathVariable int appointmentId) {

        return service.getByAppointmentId(appointmentId);
    }

    // DOCTOR: Update prescription
    @PutMapping("/{appointmentId}")
    public PrescriptionResponseDTO updatePrescription(
            @PathVariable int appointmentId,
            @Valid @RequestBody PrescriptionRequestDTO dto,
            Authentication auth) {

        return service.updatePrescription(
                appointmentId,
                dto,
                auth.getName()
        );
    }
}
