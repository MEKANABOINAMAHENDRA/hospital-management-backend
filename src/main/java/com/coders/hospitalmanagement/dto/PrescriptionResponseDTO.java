package com.coders.hospitalmanagement.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class PrescriptionResponseDTO {

    private Integer prescriptionId;
    private String diagnosis;

    // APPOINTMENT INFO
    private Integer appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;

    // DOCTOR INFO
    private Integer doctorId;
    private String doctorName;

    // PATIENT INFO
    private Integer patientId;
    private String patientName;

    // PRESCRIPTION CREATED DATE
    private LocalDateTime createdAt;

    // MEDICINES
    private List<PrescriptionItemResponseDTO> items;

    // ================= GETTERS & SETTERS =================

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<PrescriptionItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItemResponseDTO> items) {
        this.items = items;
    }
}
