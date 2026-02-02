package com.coders.hospitalmanagement.dto;

import com.coders.hospitalmanagement.model.*;

public class DoctorMapper {

    public static Doctor toEntity(DoctorRequestDTO dto) {

        Doctor d = new Doctor();

        // ✅ CORRECT SETTERS
        d.setDname(dto.getName());
        d.setSpecialization(dto.getSpecialization());
        d.setPhoneno(dto.getPhoneNo());
        d.setEmail(dto.getEmail());
        d.setExperience(dto.getExperience());
        d.setQualification(dto.getQualification());
        d.setAvailability(dto.getAvailable());

        // USER (TEMP)
        User user = new User();
        user.setId(dto.getUserId());
        d.setUser(user);

        // DEPARTMENT (TEMP)
        Department dept = new Department();
        dept.setId(dto.getDepartmentId());
        d.setDepartment(dept);

        return d;
    }

    public static DoctorResponseDTO toResponse(Doctor d) {

        DoctorResponseDTO dto = new DoctorResponseDTO();

        dto.setId(d.getDid());
        dto.setName(d.getDname());
        dto.setSpecialization(d.getSpecialization());
        dto.setPhoneNo(d.getPhoneno());
        dto.setExperience(d.getExperience());
        dto.setQualification(d.getQualification());
        dto.setAvailable(d.getAvailability());

        if (d.getDepartment() != null) {
            dto.setDepartmentId(d.getDepartment().getId());
            dto.setDepartmentName(d.getDepartment().getName());
        }

        return dto;
    }
}
