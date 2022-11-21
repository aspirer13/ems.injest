package com.rest.ems.injest.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.ems.injest.entity.DepartmentEntity;
import com.rest.ems.injest.entity.EmployeeEntity;
import com.rest.ems.injest.repository.DepartmentRepository;
import com.rest.ems.injest.repository.EmployeeRepository;

@Service
public class EmsInjestService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	public static final String EMPLOYEEDATAPATH = "C:\\Users\\anjan\\OneDrive\\Desktop\\Karun\\Application Files\\EmployeeData.csv";
	public static final String DEPARTMENTDATAPATH = "C:\\Users\\anjan\\OneDrive\\Desktop\\Karun\\Application Files\\DepartmentData.csv";

	public void addDepartmentData() {

		String line = "";
		String splitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(DEPARTMENTDATAPATH));) {

			// skip header line
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] department = line.split(splitBy);
				DepartmentEntity de = new DepartmentEntity();
				de.setName(department[0]);
				de.setDescription(department[1]);

				Optional<DepartmentEntity> optionalDe = departmentRepository.findById(de.getName());
				if (optionalDe.get() == null) {
					departmentRepository.save(de);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addEmployeeData() {

		String line = "";
		String splitBy = ",";
		try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEEDATAPATH));) {

			// skip header line
			br.readLine();

			while ((line = br.readLine()) != null) {
				String[] employee = line.split(splitBy);
				EmployeeEntity ee = new EmployeeEntity();
				ee.setId(Integer.parseInt(employee[0]));
				ee.setName(employee[1]);
				ee.setDesignation(employee[2]);
				ee.setDepartment(employee[3]);
				ee.setJoiningDate(parseStringToLocalDate(employee[4]));
				ee.setStatus(employee[5]);
				employeeRepository.save(ee);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public LocalDate parseStringToLocalDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return LocalDate.parse(dateString, formatter);
	}

}
