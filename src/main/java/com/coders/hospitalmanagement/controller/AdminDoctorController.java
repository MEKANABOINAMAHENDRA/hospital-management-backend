package com.coders.hospitalmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coders.hospitalmanagement.dto.AdminAddDoctorRequestDTO;
import com.coders.hospitalmanagement.model.Doctor;
import com.coders.hospitalmanagement.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")

public class AdminDoctorController {
	 @Autowired
	    private DoctorService doctorService;

	    @PostMapping("/doctor")
	    public Doctor addDoctor(@Valid @RequestBody AdminAddDoctorRequestDTO dto) {
	        return doctorService.createDoctorByAdmin(dto);
	    }
	

}
