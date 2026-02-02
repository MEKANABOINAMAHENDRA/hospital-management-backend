package com.coders.hospitalmanagement.dto;

import java.util.List;

import com.coders.hospitalmanagement.model.Prescription;
import com.coders.hospitalmanagement.model.PrescriptionItem;

public class PrescriptionMapper {

    public static PrescriptionResponseDTO toDTO(Prescription p) {

        PrescriptionResponseDTO dto = new PrescriptionResponseDTO();

        // PRESCRIPTION
        dto.setPrescriptionId(p.getId());
        dto.setDiagnosis(p.getDiagnosis());
        dto.setCreatedAt(p.getCreatedAt());

        // APPOINTMENT
        dto.setAppointmentId(p.getAppointment().getId());
        dto.setAppointmentDate(p.getAppointment().getAppointmentDate());
        dto.setAppointmentTime(p.getAppointment().getAppointmentTime());

        // DOCTOR
        dto.setDoctorId(p.getDoctor().getDid());
        dto.setDoctorName(p.getDoctor().getDname());

        // PATIENT
        dto.setPatientId(p.getPatient().getId());
        dto.setPatientName(p.getPatient().getName());

        // MEDICINES
        List<PrescriptionItemResponseDTO> items =
                p.getItems() == null
                    ? List.of()
                    : p.getItems().stream()
                          .map(PrescriptionMapper::mapItem)
                          .toList();

        dto.setItems(items);

        return dto;
    }

    private static PrescriptionItemResponseDTO mapItem(PrescriptionItem item) {

        PrescriptionItemResponseDTO dto = new PrescriptionItemResponseDTO();
        dto.setMedicineName(item.getMedicineName());
        dto.setDosage(item.getDosage());
        dto.setDuration(item.getDuration());
        dto.setInstructions(item.getInstructions());
        return dto;
    }
}
