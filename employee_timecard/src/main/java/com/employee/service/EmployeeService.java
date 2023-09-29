package com.employee.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.utility.ReadDataFormExcel;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public void save(MultipartFile file) {
		try {
			List<Employee> listEmployee = ReadDataFormExcel.convertExcelToListOfUser(file.getInputStream());
			employeeRepository.saveAll(listEmployee);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	public List<Employee> findEmployeesWorkedFor7ConsecutiveDays() {
		return employeeRepository.findEmployeesWorkedFor7ConsecutiveDays();

	}

	public List<Employee> findEmployeesWithShortBreaks() {
		return employeeRepository.findEmployeesWithShortBreaks();

	}

	public List<Employee> findEmployeesWithLongShifts() {
		return employeeRepository.findEmployeesWithLongShifts();
	}

}
