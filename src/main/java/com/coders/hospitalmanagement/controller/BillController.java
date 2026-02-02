package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.coders.hospitalmanagement.dto.BillRequestDTO;
import com.coders.hospitalmanagement.dto.BillResponseDTO;
import com.coders.hospitalmanagement.service.BillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService service;

    // ===============================
    // ADMIN: Create Bill (PENDING)
    // ===============================
    @PostMapping("/{appointmentId}")
    public BillResponseDTO createBill(
            @PathVariable int appointmentId,
            @Valid @RequestBody BillRequestDTO dto) {

        return service.createBill(appointmentId, dto);
    }

    // ===============================
    // ADMIN / PATIENT: Get bill by appointment
    // ===============================
    @GetMapping("/appointment/{appointmentId}")
    public BillResponseDTO getByAppointment(
            @PathVariable int appointmentId) {

        return service.getByAppointmentId(appointmentId);
    }

    // ===============================
    // PATIENT: Bill history
    // ===============================
    @GetMapping("/my")
    public List<BillResponseDTO> myBills(Authentication auth) {

        return service.getMyBills(auth.getName());
    }

    // ===============================
    // PATIENT / ADMIN: Pay bill
    // ===============================
    @PatchMapping("/{billId}/pay/{mode}")
    public BillResponseDTO payBill(
            @PathVariable int billId,
            @PathVariable String mode) {

        return service.payBill(billId, mode);
    }
}
