package com.employee.utility;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.employee.entity.Employee;
import com.employee.enums.PositionStatus;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Component
public class ReadDataFormExcel {

	public static boolean checkExcelFormat(MultipartFile file) {
		return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	}

	public static List<Employee> convertExcelToListOfUser(InputStream is) {
		List<Employee> employeeData = new ArrayList<>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			int rowNumber = 0;
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row row = iterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Employee employee = new Employee();
				Iterator<Cell> cells = row.iterator();
				int cid = 0;
				while (cells.hasNext()) {
					Cell cell = cells.next();
					switch (cid) {
					case 0:
						if (cell.getCellType() == CellType.STRING) {
							employee.setPositionId(cell.getStringCellValue());
						}
						break;
					case 1:
						employee.setPositionStatus(PositionStatus.ACTIVE);
						break;
					case 2:
						if (cell.getCellType() == CellType.NUMERIC) {
							employee.setTime(cell.getLocalDateTimeCellValue());
						} else if (cell.getCellType() == CellType.STRING) {
							try {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
								LocalDateTime dateTime = LocalDateTime.parse(cell.getStringCellValue(), formatter);
								employee.setTime(dateTime);
							} catch (DateTimeParseException ex) {
								ex.printStackTrace();
							}
						}
						break;
					case 3:
						if (cell.getCellType() == CellType.NUMERIC) {
							employee.setTimeOut(cell.getLocalDateTimeCellValue());
						} else if (cell.getCellType() == CellType.STRING) {
							try {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
								LocalDateTime dateTime = LocalDateTime.parse(cell.getStringCellValue(), formatter);
								employee.setTimeOut(dateTime);
							} catch (DateTimeParseException ex) {
								ex.printStackTrace();
							}
						}
						break;
					case 4:
						if (cell.getCellType() == CellType.STRING) {
							String timecardHoursStr = cell.getStringCellValue();
							String[] parts = timecardHoursStr.split(":");
							if (parts.length == 2) {
								int hours = Integer.parseInt(parts[0]);
								int minutes = Integer.parseInt(parts[1]);
								LocalTime timecardHours = LocalTime.of(hours, minutes);
								employee.setTimecardHours(timecardHours);
							}
						}
						break;

					case 5:
						if (cell.getCellType() == CellType.NUMERIC) {
							LocalDate payCycleStartDate = cell.getLocalDateTimeCellValue().toLocalDate();
							employee.setPayCycleStartDate(payCycleStartDate);
						} else if (cell.getCellType() == CellType.STRING) {
							try {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
								LocalDate payCycleStartDate = LocalDate.parse(cell.getStringCellValue(), formatter);
								employee.setPayCycleStartDate(payCycleStartDate);
							} catch (DateTimeParseException ex) {
								ex.printStackTrace();
							}
						}
						break;
					case 6:
						if (cell.getCellType() == CellType.NUMERIC) {
							LocalDate payCycleEndDate = cell.getLocalDateTimeCellValue().toLocalDate();
							employee.setPayCycleEndDate(payCycleEndDate);
						} else if (cell.getCellType() == CellType.STRING) {
							try {
								DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
								LocalDate payCycleEndDate = LocalDate.parse(cell.getStringCellValue(), formatter);
								employee.setPayCycleEndDate(payCycleEndDate);
							} catch (DateTimeParseException ex) {
								ex.printStackTrace();
							}
						}
						break;
					case 7:
						if (cell.getCellType() == CellType.STRING) {
							employee.setEmployeeName(cell.getStringCellValue());
						}
						break;
					case 8:
						if (cell.getCellType() == CellType.NUMERIC) {
							employee.setFileNumber((long) cell.getNumericCellValue());
						} else if (cell.getCellType() == CellType.STRING) {
							try {
								employee.setFileNumber(Long.parseLong(cell.getStringCellValue()));
							} catch (NumberFormatException ex) {
								ex.printStackTrace();
							}
						}
						break;
					}
					System.err.println("Pass" + cid);
					cid++;
				}
				employeeData.add(employee);
			}
			return employeeData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return employeeData;
	}

}
