package com.coders.hospitalmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.dto.PatientMapper;
import com.coders.hospitalmanagement.dto.PatientRequestDTO;
import com.coders.hospitalmanagement.dto.PatientResponseDTO;
import com.coders.hospitalmanagement.model.Patient;
import com.coders.hospitalmanagement.service.PatientService;

@RestController
@CrossOrigin(
		  origins = {
		    "http://localhost:5173",
		    "https://hospital-management-frontend-7wqf.onrender.com"
		  }
		)

public class PatientController {

    @Autowired
    private PatientService service;

    /* ================= ADMIN / NURSE ================= */

    @PostMapping("/patient")
    public PatientResponseDTO addPatient(@RequestBody PatientRequestDTO dto) {
        Patient patient = PatientMapper.toEntity(dto);
        Patient saved = service.insertData(patient, dto.getUserId());
        return PatientMapper.toResponse(saved);
    }

    @GetMapping("/patient")
    public List<PatientResponseDTO> getAllPatients() {
        return service.AllPatientDetails()
                .stream()
                .map(PatientMapper::toResponse)
                .toList();
    }

    @GetMapping("/patient/{id}")
    public PatientResponseDTO getPatient(@PathVariable int id) {
        return PatientMapper.toResponse(service.patientById(id));
    }

    @PutMapping("/patient/{id}")
    public PatientResponseDTO updatePatient(
            @PathVariable int id,
            @RequestBody PatientRequestDTO dto) {

        Patient patient = PatientMapper.toEntity(dto);
        return PatientMapper.toResponse(
                service.updateAllPatients(id, patient)
        );
    }

    @DeleteMapping("/patient/{id}")
    public PatientResponseDTO deletePatient(@PathVariable int id) {
        return PatientMapper.toResponse(service.deletePatientById(id));
    }

    /* ================= PATIENT (JWT) ================= */

    @GetMapping("/patient/me")
    public ResponseEntity<PatientResponseDTO> getMyProfile(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName();
        Patient patient = service.getPatientByUsername(username);

        // ✅ CORRECT: new user
        if (patient == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                PatientMapper.toResponse(patient)
        );
    }


    @PostMapping("/patient/create-profile")
    public PatientResponseDTO createMyProfile(
            Authentication authentication,
            @RequestBody PatientRequestDTO dto) {

        String username = authentication.getName();

        Patient saved = service.createProfileForUser(username, dto);

        return PatientMapper.toResponse(saved);
    }

    @PatchMapping("/patient/me")
    public PatientResponseDTO updateMyProfile(
            Authentication authentication,
            @RequestBody Patient p) {

        return PatientMapper.toResponse(
                service.updatePatientByUsername(authentication.getName(), p)
        );
    }

    /* ================= SEARCH ================= */

    @GetMapping("/patient/search")
    public ResponseEntity<?> searchByPhone(@RequestParam String phone) {

        Optional<Patient> patient = service.findByPhone(phone);

        if (patient.isPresent()) {
            return ResponseEntity.ok(
                    PatientMapper.toResponse(patient.get())
            );
        }

        return ResponseEntity.ok(null);
    }
    @PostMapping("/patient/offline")
    public PatientResponseDTO addOfflinePatient(
            @RequestBody PatientRequestDTO dto) {

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhoneno(dto.getPhoneno());
        patient.setAddress(dto.getAddress());

        Patient saved = service.saveOfflinePatient(patient);

        return PatientMapper.toResponse(saved);
    }

}
