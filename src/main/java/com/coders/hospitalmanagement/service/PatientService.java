package com.coders.hospitalmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.dto.PatientRequestDTO;
import com.coders.hospitalmanagement.model.Patient;
import com.coders.hospitalmanagement.model.User;
import com.coders.hospitalmanagement.repository.PatientRepository;
import com.coders.hospitalmanagement.repository.UserRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository repo;

    @Autowired
    private UserRepository userRepo;

    /* ================= ADMIN / NURSE ================= */

    // OFFLINE + ADMIN CREATE
    public Patient insertData(Patient patient, Integer userId) {

        // OFFLINE WALK-IN (NURSE)
        if (userId == null) {
            patient.setUser(null);
            return repo.save(patient);
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        patient.setUser(user);
        return repo.save(patient);
    }

    public List<Patient> AllPatientDetails() {
        return repo.findAll();
    }

    public Patient patientById(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public Patient deletePatientById(int id) {
        Patient p = patientById(id);
        repo.delete(p);
        return p;
    }

    public Patient updateAllPatients(int id, Patient p) {

        Patient existing = patientById(id);

        existing.setName(p.getName());
        existing.setAge(p.getAge());
        existing.setGender(p.getGender());
        existing.setPhoneno(p.getPhoneno());
        existing.setAddress(p.getAddress());

        return repo.save(existing);
    }

    /* ================= PATIENT (JWT) ================= */

    // ✅ FIX: DO NOT THROW EXCEPTION
    public Patient getPatientByUsername(String username) {
        return repo.findByUserUsername(username).orElse(null);
    }

    // ✅ CREATE PROFILE AFTER LOGIN
    public Patient createProfileForUser(String username, Patient patientData) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // prevent duplicate profile
        Optional<Patient> existing = repo.findByUserUsername(username);
        if (existing.isPresent()) {
            throw new RuntimeException("Profile already exists");
        }

        Patient patient = new Patient();
        patient.setName(patientData.getName());
        patient.setAge(patientData.getAge());
        patient.setGender(patientData.getGender());
        patient.setPhoneno(patientData.getPhoneno());
        patient.setAddress(patientData.getAddress());
        patient.setUser(user);

        return repo.save(patient);
    }

    public Patient updatePatientByUsername(String username, Patient p) {

        Patient existing = getPatientByUsername(username);

        if (existing == null) {
            throw new RuntimeException("Patient profile not found");
        }

        if (p.getPhoneno() != null)
            existing.setPhoneno(p.getPhoneno());

        if (p.getAddress() != null)
            existing.setAddress(p.getAddress());

        return repo.save(existing);
    }

    /* ================= SEARCH ================= */

    public Optional<Patient> findByPhone(String phone) {
        return repo.findByPhoneno(phone);
    }
 // ================= CREATE PROFILE (ONLINE PATIENT) =================
    public Patient createProfileForUser(String username, PatientRequestDTO dto) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // prevent duplicate patient profile
        Optional<Patient> existing = repo.findByUserUsername(username);
        if (existing.isPresent()) {
            throw new RuntimeException("Patient profile already exists");
        }

        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setPhoneno(dto.getPhoneno());
        patient.setAddress(dto.getAddress());
        patient.setUser(user);

        return repo.save(patient);
    }
    public Patient saveOfflinePatient(Patient patient) {
        // ❗ NO user mapping here
        return repo.save(patient);
    }


}
