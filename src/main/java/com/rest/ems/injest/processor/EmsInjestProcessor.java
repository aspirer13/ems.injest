package com.rest.ems.injest.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.ems.injest.service.EmsInjestService;

@Component
public class EmsInjestProcessor {

	@Autowired
	EmsInjestService service;

	public void injestTableData() {

		service.addDepartmentData();
		service.addEmployeeData();

	}
}
