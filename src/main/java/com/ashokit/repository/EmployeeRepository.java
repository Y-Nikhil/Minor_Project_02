package com.ashokit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashokit.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	public Employee findByEmail(String email);

	public Employee findByEmailAndPassword(String email, String password);

	
	
}
