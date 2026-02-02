package com.coders.hospitalmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coders.hospitalmanagement.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{

	 Optional<Department> findByNameIgnoreCase(String name);
}
