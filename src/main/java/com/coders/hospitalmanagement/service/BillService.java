package com.coders.hospitalmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.dto.BillMapper;
import com.coders.hospitalmanagement.dto.BillRequestDTO;
import com.coders.hospitalmanagement.dto.BillResponseDTO;
import com.coders.hospitalmanagement.model.Appointment;
import com.coders.hospitalmanagement.model.Bill;
import com.coders.hospitalmanagement.repository.AppointmentRepository;
import com.coders.hospitalmanagement.repository.BillRepository;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // ===============================
    public BillResponseDTO createBill(int appointmentId, BillRequestDTO dto) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (billRepo.findByAppointment_Id(appointmentId).isPresent()) {
            throw new RuntimeException("Bill already exists for this appointment");
        }

        Bill bill = new Bill();
        bill.setAmount(dto.getAmount());
        bill.setPaymentStatus("PENDING");
        bill.setPaymentMode("NOT_PAID"); // not paid yet
        bill.setAppointment(appointment);

        return BillMapper.toDTO(billRepo.save(bill));
    }



    // ===============================
    // ADMIN / PATIENT: Get bill by appointment
    // ===============================
    public BillResponseDTO getByAppointmentId(int appointmentId) {

        Bill bill = billRepo.findByAppointment_Id(appointmentId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        return BillMapper.toDTO(bill);
    }

    // ===============================
    // PATIENT: Bill history
    // ===============================
    public List<BillResponseDTO> getMyBills(String username) {

        return billRepo.findByAppointment_Patient_User_Username(username)
                .stream()
                .map(BillMapper::toDTO)
                .toList();
    }

    // ===============================
    // PATIENT / ADMIN: Pay bill
    // ===============================
    public BillResponseDTO payBill(int billId, String mode) {

        Bill bill = billRepo.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        if ("PAID".equals(bill.getPaymentStatus())) {
            throw new RuntimeException("Bill already paid");
        }

        bill.setPaymentStatus("PAID");
        bill.setPaymentMode(mode); // CASH / CARD / UPI

        return BillMapper.toDTO(billRepo.save(bill));
    }
}
