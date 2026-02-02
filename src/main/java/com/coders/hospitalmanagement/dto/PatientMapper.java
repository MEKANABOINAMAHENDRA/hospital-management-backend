package com.coders.hospitalmanagement.dto;

import com.coders.hospitalmanagement.model.Patient;

public class PatientMapper {

    public static Patient toEntity(PatientRequestDTO dto) {
        Patient p = new Patient();
        p.setName(dto.getName());
        p.setAge(dto.getAge());
        p.setGender(dto.getGender());
        p.setPhoneno(dto.getPhoneno());
        p.setAddress(dto.getAddress());
        return p;
    }

    public static PatientResponseDTO toResponse(Patient p) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setAge(p.getAge());
        dto.setGender(p.getGender());
        dto.setPhoneno(p.getPhoneno());
        dto.setAddress(p.getAddress());
        return dto;
    }
}
