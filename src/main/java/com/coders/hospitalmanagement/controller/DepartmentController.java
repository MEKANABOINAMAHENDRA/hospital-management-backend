package com.coders.hospitalmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coders.hospitalmanagement.model.Department;
import com.coders.hospitalmanagement.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
public class DepartmentController {
	@Autowired
    DepartmentService service;

    @PostMapping("/department")
    public Department addDepartment(@Valid @RequestBody Department d) {
        return service.addDepartment(d);
    }

    @GetMapping("/department")
    public List<Department> getAllDepartments() {
        return service.getAllDepartments();
    }

    @GetMapping("/department/{id}")
    public Department getDepartment(@PathVariable int id) {
        return service.getDepartmentById(id);
    }

    @DeleteMapping("/department/{id}")
    public Department deleteDepartment(@PathVariable int id) {
        return service.deleteDepartment(id);
    }

}
