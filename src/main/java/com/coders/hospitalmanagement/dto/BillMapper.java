package com.coders.hospitalmanagement.dto;

import com.coders.hospitalmanagement.model.Bill;

public class BillMapper {

    public static BillResponseDTO toDTO(Bill bill) {

        BillResponseDTO dto = new BillResponseDTO();

        dto.setBillId(bill.getId());
        dto.setAmount(bill.getAmount());
        dto.setPaymentStatus(bill.getPaymentStatus());
        dto.setPaymentMode(bill.getPaymentMode());

        dto.setBillDate(bill.getBillDate()); // ✅ FIX

        dto.setAppointmentId(bill.getAppointment().getId());
        dto.setPatientId(bill.getAppointment().getPatient().getId());
        dto.setPatientName(bill.getAppointment().getPatient().getName());
        dto.setDoctorId(bill.getAppointment().getDoctor().getDid());
        dto.setDoctorName(bill.getAppointment().getDoctor().getDname());

        return dto;
    }
}
