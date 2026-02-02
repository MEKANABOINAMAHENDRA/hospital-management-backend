package com.coders.hospitalmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.dto.AdminAddDoctorRequestDTO;
import com.coders.hospitalmanagement.dto.DoctorRequestDTO;
import com.coders.hospitalmanagement.model.*;
import com.coders.hospitalmanagement.repository.*;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ================= ADMIN CREATE =================
    public Doctor createDoctorByAdmin(AdminAddDoctorRequestDTO dto) {

        // 1️⃣ Create USER
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_DOCTOR");
        user = userRepo.save(user);

        // 2️⃣ Fetch Department
        Department dept = deptRepo.findById(dto.getDepartmentId())
            .orElseThrow(() -> new RuntimeException("Department not found"));

        // 3️⃣ Create Doctor
        Doctor d = new Doctor();
        d.setUser(user);
        d.setDname(dto.getName());
        d.setSpecialization(dto.getSpecialization());
        d.setPhoneno(dto.getPhoneNo());
        d.setEmail(dto.getEmail());
        d.setExperience(dto.getExperience());
        d.setQualification(dto.getQualification());
        d.setAvailability(true);   // ✅ FIXED
        d.setDepartment(dept);

        return doctorRepo.save(d);
    }

    // ================= READ =================
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctorById(int id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor getDoctorByUsername(String username) {
        return doctorRepo.findByUserUsername(username).orElse(null);
    }

    // ================= UPDATE =================
    public Doctor updateDoctor(int id, DoctorRequestDTO dto) {

        Doctor d = getDoctorById(id);

        d.setDname(dto.getName());
        d.setSpecialization(dto.getSpecialization());
        d.setPhoneno(dto.getPhoneNo());
        d.setEmail(dto.getEmail());
        d.setExperience(dto.getExperience());
        d.setQualification(dto.getQualification());
        d.setAvailability(dto.getAvailable());

        d.setDepartment(
            deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"))
        );

        return doctorRepo.save(d);
    }

    // ================= PATCH =================
    public Doctor patchDoctor(int id, DoctorRequestDTO dto) {

        Doctor d = getDoctorById(id);

        if (dto.getAvailable() != null)
            d.setAvailability(dto.getAvailable());

        if (dto.getDepartmentId() != null)
            d.setDepartment(
                deptRepo.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"))
            );

        return doctorRepo.save(d);
    }

    // ================= DELETE =================
    public Doctor deleteDoctor(int id) {
        Doctor d = getDoctorById(id);
        doctorRepo.delete(d);
        return d;
    }
}
