package com.coders.hospitalmanagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.dto.AppointmentRequestDTO;
import com.coders.hospitalmanagement.dto.OfflineAppointmentDTO;
import com.coders.hospitalmanagement.model.Appointment;
import com.coders.hospitalmanagement.model.Doctor;
import com.coders.hospitalmanagement.model.Patient;
import com.coders.hospitalmanagement.repository.AppointmentRepository;
import com.coders.hospitalmanagement.repository.DoctorRepository;
import com.coders.hospitalmanagement.repository.PatientRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    // ================= ONLINE BOOKING (PATIENT) =================
    public Appointment bookOnline(
            AppointmentRequestDTO dto,
            String username) {
    	


        Patient patient = patientRepo.findByUserUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());

        // Online booking → nurse approval required
        appointment.setStatus("PENDING");

        return appointmentRepo.save(appointment);
        
    }
    

    // ================= OFFLINE BOOKING (NURSE) =================
    public Appointment bookOffline(OfflineAppointmentDTO dto) {

        Doctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        // 🔥 CORRECT: phoneno everywhere
        Patient patient = patientRepo.findByPhoneno(dto.getPhoneno())
                .orElseGet(() -> {
                    Patient p = new Patient();
                    p.setName(dto.getName());
                    p.setAge(dto.getAge());
                    p.setGender(dto.getGender());
                    p.setPhoneno(dto.getPhoneno());
                    return patientRepo.save(p);
                });

        int serialNo = appointmentRepo.countTodayAppointments(
                doctor.getDid(),
                dto.getAppointmentDate()
        ) + 1;

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setSerialNo(serialNo);

        // Offline booking → direct confirm
        appointment.setStatus("BOOKED");

        return appointmentRepo.save(appointment);
    }

    // ================= NURSE TODAY APPOINTMENTS =================
    public List<Appointment> getTodayAppointments() {
        return appointmentRepo
                .findByAppointmentDateAndStatusInOrderBySerialNoAsc(
                        LocalDate.now(),
                        List.of("BOOKED")
                );
    }

    // ================= STATUS FILTER =================
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepo.findByStatus(status);
    }

    // ================= UPDATE STATUS =================
    public Appointment updateStatus(int id, String status) {
        Appointment appointment = appointmentRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        appointment.setStatus(status);
        return appointmentRepo.save(appointment);
    }

    // ================= PATIENT DASHBOARD =================
    public List<Appointment> getAppointmentsByPatientUsername(String username) {
        return appointmentRepo.findByPatient_User_Username(username);
    }

    // ================= DOCTOR DASHBOARD =================
    public List<Appointment> getAppointmentsByDoctorUsername(String username) {
        return appointmentRepo.findByDoctor_User_UsernameAndStatusIn(
                username,
                List.of("BOOKED", "COMPLETED")
        );
    }
 // ================= ADMIN =================
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    

}
