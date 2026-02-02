package com.coders.hospitalmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coders.hospitalmanagement.exception.ResourceNotFoundException;
import com.coders.hospitalmanagement.model.Department;
import com.coders.hospitalmanagement.repository.DepartmentRepository;

@Service
public class DepartmentService {

	@Autowired
    DepartmentRepository repo;

    public Department addDepartment(Department d) {
        return repo.save(d);
    }

    public List<Department> getAllDepartments() {
        return repo.findAll();
    }

    public Department getDepartmentById(int id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department not found with id " + id));
    }

    public Department deleteDepartment(int id) {
        Department d = getDepartmentById(id);
        repo.deleteById(id);
        return d;
    }
}
