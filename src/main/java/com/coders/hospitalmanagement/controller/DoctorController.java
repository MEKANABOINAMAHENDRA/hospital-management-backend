package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.dto.*;
import com.coders.hospitalmanagement.model.Doctor;
import com.coders.hospitalmanagement.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    private DoctorService service;

    // ================= ADMIN CREATE DOCTOR =================
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO addDoctorByAdmin(
            @Valid @RequestBody AdminAddDoctorRequestDTO dto) {

        Doctor doctor = service.createDoctorByAdmin(dto);
        return DoctorMapper.toResponse(doctor);
    }

    // ================= GET =================
    @GetMapping
    public List<DoctorResponseDTO> getAllDoctors() {
        return service.getAllDoctors()
                .stream()
                .map(DoctorMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO getDoctor(@PathVariable int id) {
        return DoctorMapper.toResponse(service.getDoctorById(id));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO updateDoctor(
            @PathVariable int id,
            @RequestBody DoctorRequestDTO dto) {

        return DoctorMapper.toResponse(service.updateDoctor(id, dto));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO patchDoctor(
            @PathVariable int id,
            @RequestBody DoctorRequestDTO dto) {

        return DoctorMapper.toResponse(service.patchDoctor(id, dto));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DoctorResponseDTO deleteDoctor(@PathVariable int id) {
        return DoctorMapper.toResponse(service.deleteDoctor(id));
    }

    // ================= DOCTOR PROFILE =================
    @GetMapping("/me")
  
    public ResponseEntity<?> getMyProfile(Authentication authentication) {

        Doctor doctor = service.getDoctorByUsername(authentication.getName());

        if (doctor == null) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(
            DoctorMapper.toResponse(doctor)
        );
    }
}
