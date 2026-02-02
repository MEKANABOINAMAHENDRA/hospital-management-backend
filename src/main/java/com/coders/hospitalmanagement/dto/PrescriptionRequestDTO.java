package com.coders.hospitalmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class PrescriptionRequestDTO {

    @NotBlank
    private String diagnosis;

    @NotEmpty
    private List<PrescriptionItemRequestDTO> items;

    // getters & setters
    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<PrescriptionItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<PrescriptionItemRequestDTO> items) {
        this.items = items;
    }
}
