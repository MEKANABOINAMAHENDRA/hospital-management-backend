package com.coders.hospitalmanagement.dto;

import jakarta.validation.constraints.NotBlank;

public class PrescriptionItemRequestDTO {

    @NotBlank
    private String medicineName;

    @NotBlank
    private String dosage;        // 1-0-1

    @NotBlank
    private String duration;      // 5 days

    @NotBlank
    private String instructions;  // After food

    // getters & setters
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
