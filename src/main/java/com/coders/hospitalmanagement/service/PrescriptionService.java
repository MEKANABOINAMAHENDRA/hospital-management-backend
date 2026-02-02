package com.coders.hospitalmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.dto.PrescriptionMapper;
import com.coders.hospitalmanagement.dto.PrescriptionRequestDTO;
import com.coders.hospitalmanagement.dto.PrescriptionResponseDTO;
import com.coders.hospitalmanagement.model.Appointment;
import com.coders.hospitalmanagement.model.Prescription;
import com.coders.hospitalmanagement.model.PrescriptionItem;
import com.coders.hospitalmanagement.repository.AppointmentRepository;
import com.coders.hospitalmanagement.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // =================================================
    // DOCTOR: Create prescription
    // =================================================
    public PrescriptionResponseDTO createPrescription(
            int appointmentId,
            PrescriptionRequestDTO dto,
            String doctorUsername) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getDoctor().getUser().getUsername().equals(doctorUsername)) {
            throw new RuntimeException("Not allowed");
        }

        // ✅ STRING STATUS CHECK
        if ("COMPLETED".equalsIgnoreCase(appointment.getStatus())) {
            throw new RuntimeException("Appointment already completed");
        }

        if (prescriptionRepo.findByAppointment_Id(appointmentId).isPresent()) {
            throw new RuntimeException("Prescription already exists");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("At least one medicine is required");
        }

        Prescription prescription = new Prescription();
        prescription.setDiagnosis(dto.getDiagnosis());
        prescription.setAppointment(appointment);
        prescription.setDoctor(appointment.getDoctor());
        prescription.setPatient(appointment.getPatient());

        List<PrescriptionItem> items = dto.getItems().stream().map(i -> {
            PrescriptionItem item = new PrescriptionItem();
            item.setMedicineName(i.getMedicineName());
            item.setDosage(i.getDosage());
            item.setDuration(i.getDuration());
            item.setInstructions(i.getInstructions());
            item.setPrescription(prescription);
            return item;
        }).toList();

        prescription.setItems(items);

        return PrescriptionMapper.toDTO(prescriptionRepo.save(prescription));
    }

    // =================================================
    // PATIENT: View own prescriptions
    // =================================================
    public List<PrescriptionResponseDTO> getPrescriptionsForPatient(String username) {

        return prescriptionRepo
                .findByAppointment_Patient_User_Username(username)
                .stream()
                .map(PrescriptionMapper::toDTO)
                .toList();
    }

    // =================================================
    // DOCTOR: View prescriptions written by him
    // =================================================
    public List<PrescriptionResponseDTO> getPrescriptionsForDoctor(String username) {

        return prescriptionRepo
                .findByAppointment_Doctor_User_Username(username)
                .stream()
                .map(PrescriptionMapper::toDTO)
                .toList();
    }

    // =================================================
    // ADMIN: View all prescriptions
    // =================================================
    public List<PrescriptionResponseDTO> getAllPrescriptions() {

        return prescriptionRepo.findAll()
                .stream()
                .map(PrescriptionMapper::toDTO)
                .toList();
    }

    

 // View by appointment (FIXED)
 // =================================================
 public PrescriptionResponseDTO getByAppointmentId(int appointmentId) {

     return prescriptionRepo
             .findByAppointment_Id(appointmentId)
             .map(PrescriptionMapper::toDTO)
             .orElse(null);   // ✅ IMPORTANT
 }

    // =================================================
    // DOCTOR: Update prescription
    // =================================================
    public PrescriptionResponseDTO updatePrescription(
            int appointmentId,
            PrescriptionRequestDTO dto,
            String doctorUsername) {

        Prescription prescription = prescriptionRepo
                .findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));

        if (!prescription.getDoctor().getUser().getUsername().equals(doctorUsername)) {
            throw new RuntimeException("Not allowed");
        }

        // ✅ LOCK AFTER COMPLETION
        if ("COMPLETED".equalsIgnoreCase(prescription.getAppointment().getStatus())) {
            throw new RuntimeException("Prescription is locked");
        }

        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new RuntimeException("At least one medicine is required");
        }

        prescription.setDiagnosis(dto.getDiagnosis());

        prescription.getItems().clear();

        List<PrescriptionItem> items = dto.getItems().stream().map(i -> {
            PrescriptionItem item = new PrescriptionItem();
            item.setMedicineName(i.getMedicineName());
            item.setDosage(i.getDosage());
            item.setDuration(i.getDuration());
            item.setInstructions(i.getInstructions());
            item.setPrescription(prescription);
            return item;
        }).toList();

        prescription.getItems().addAll(items);

        return PrescriptionMapper.toDTO(prescriptionRepo.save(prescription));
    }
}
