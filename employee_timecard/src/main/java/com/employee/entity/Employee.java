package com.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.employee.enums.PositionStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String positionId;

	@Enumerated(EnumType.STRING)
	private PositionStatus positionStatus;

	private LocalDateTime time;
	private LocalDateTime timeOut;
	private LocalTime timecardHours;
	private LocalDate payCycleStartDate;
	private LocalDate payCycleEndDate;
	private String employeeName;
	
	@Column(name ="file_number")
	private Long fileNumber;	
}
