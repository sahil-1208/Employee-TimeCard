package com.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employee.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query(value = "SELECT e.id, e.employee_name,e.time,e.file_number,e.pay_cycle_end_date,e.pay_cycle_start_date,e.position_id,e.position_status,e.time,e.time_out,e.timecard_hours FROM employee e "
			+ "WHERE e.time >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " + "GROUP BY e.id, e.employee_name "
			+ "HAVING COUNT(DISTINCT DATE(e.time)) = 7", nativeQuery = true)
	List<Employee> findEmployeesWorkedFor7ConsecutiveDays();

	@Query(value = "SELECT e.id, e.employee_name,e.time,e.file_number,e.pay_cycle_end_date,e.pay_cycle_start_date,e.position_id,e.position_status,e.time,e.time_out,e.timecard_hours FROM employee e "
			+ "WHERE TIMESTAMPDIFF(SECOND, e.time, (SELECT MIN(e2.time) FROM employee e2 WHERE e2.id = e.id AND e2.time > e.time)) > 3600 "
			+ "AND TIMESTAMPDIFF(SECOND, e.time, (SELECT MIN(e3.time) FROM employee e3 WHERE e3.id = e.id AND e3.time > e.time)) < 36000 "
			+ "GROUP BY e.id, e.employee_name", nativeQuery = true)
	List<Employee> findEmployeesWithShortBreaks();

	@Query(value = "SELECT e.id, e.employee_name,e.time,e.file_number,e.pay_cycle_end_date,e.pay_cycle_start_date,e.position_id,e.position_status,e.time,e.time_out,e.timecard_hours FROM employee e "
			+ "WHERE TIMESTAMPDIFF(SECOND, e.time, e.time_out) > 14 * 3600", nativeQuery = true)
	List<Employee> findEmployeesWithLongShifts();

}
