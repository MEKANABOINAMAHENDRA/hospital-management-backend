package com.coders.hospitalmanagement.dto;

import com.coders.hospitalmanagement.model.Appointment;

public class AppointmentMapper {

    public static AppointmentResponseDTO toResponse(Appointment a) {

        AppointmentResponseDTO dto = new AppointmentResponseDTO();

        dto.setId(a.getId());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentTime(a.getAppointmentTime());
        dto.setStatus(a.getStatus());

        // 🔹 SERIAL NUMBER
        dto.setSerialNo(a.getSerialNo());

        // patient
        dto.setPatientId(a.getPatient().getId());
        dto.setPatientName(a.getPatient().getName());

        // doctor
        dto.setDoctorId(a.getDoctor().getDid());
        dto.setDoctorName(a.getDoctor().getDname());
        dto.setDoctorSpecialization(a.getDoctor().getSpecialization());

        // department (SAFE)
        if (a.getDoctor().getDepartment() != null) {
            dto.setDepartmentId(a.getDoctor().getDepartment().getId());
            dto.setDepartmentName(a.getDoctor().getDepartment().getName());
        }

        return dto;
    }
}
