package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.dto.*;
import com.coders.hospitalmanagement.model.Patient;
import com.coders.hospitalmanagement.repository.PatientRepository;
import com.coders.hospitalmanagement.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService service;
    


    // ================= PATIENT ONLINE =================
    @PostMapping
    public AppointmentResponseDTO onlineBooking(
            Authentication authentication,
            @Valid @RequestBody AppointmentRequestDTO dto) {

        return AppointmentMapper.toResponse(
                service.bookOnline(dto, authentication.getName())
        );
    }

    // ================= PATIENT =================
    @GetMapping("/my")
    public List<AppointmentResponseDTO> myAppointments(Authentication auth) {
        return service.getAppointmentsByPatientUsername(auth.getName())
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    // ================= DOCTOR =================
    @GetMapping("/doctor/my")
    public List<AppointmentResponseDTO> doctorAppointments(Authentication auth) {
        return service.getAppointmentsByDoctorUsername(auth.getName())
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    // ================= NURSE ONLINE REQUESTS =================
    @GetMapping("/pending")
    public List<AppointmentResponseDTO> pendingAppointments() {
        return service.getAppointmentsByStatus("PENDING")
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    @PatchMapping("/approve/{id}")
    public AppointmentResponseDTO approve(@PathVariable int id) {
        return AppointmentMapper.toResponse(
                service.updateStatus(id, "BOOKED")
        );
    }

    @PatchMapping("/reject/{id}")
    public AppointmentResponseDTO reject(@PathVariable int id) {
        return AppointmentMapper.toResponse(
                service.updateStatus(id, "REJECTED")
        );
    }

    // ================= NURSE TODAY =================
    @GetMapping("/today")
    public List<AppointmentResponseDTO> todayAppointments() {
        return service.getTodayAppointments()
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

    // ================= DOCTOR COMPLETE =================
    @PatchMapping("/complete/{id}")
    public AppointmentResponseDTO complete(@PathVariable int id) {
        return AppointmentMapper.toResponse(
                service.updateStatus(id, "COMPLETED")
        );
    }

    // ================= NURSE OFFLINE BOOKING =================
    @PostMapping("/offline")
    public AppointmentResponseDTO offlineBooking(
            @Valid @RequestBody OfflineAppointmentDTO dto) {

        return AppointmentMapper.toResponse(
                service.bookOffline(dto)
        );
    }
 // ================= ADMIN - ALL APPOINTMENTS =================
    @GetMapping
    public List<AppointmentResponseDTO> allAppointments() {
        return service.getAllAppointments()
                .stream()
                .map(AppointmentMapper::toResponse)
                .toList();
    }

   
}
