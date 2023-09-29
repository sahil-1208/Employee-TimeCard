package com.employee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.employee.entity.Employee;
import com.employee.service.EmployeeService;
import com.employee.utility.ReadDataFormExcel;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<?> uploadCustomerExcel(@RequestParam("file") MultipartFile file) {

		if (ReadDataFormExcel.checkExcelFormat(file)) {
			employeeService.save(file);
			return ResponseEntity.ok(Map.of("message", "file is uploaded"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("uplod excel file");

	}
	
	@GetMapping("/all")
	public List<Employee> findAll(){
		return employeeService.getAll();
	}
	
	@GetMapping("/workedFor7ConsecutiveDays")
	public List<Employee> findEmployeesWorkedFor7ConsecutiveDays() {
		return employeeService.findEmployeesWorkedFor7ConsecutiveDays();

	}

	@GetMapping("/shortBreaks")
	public List<Employee> findEmployeesWithShortBreaks() {
		return employeeService.findEmployeesWithShortBreaks();

	}

	@GetMapping("/longShifts")
	public List<Employee> findEmployeesWithLongShifts() {
		return employeeService.findEmployeesWithLongShifts();
	}
	
}
