package com.employee.emplogin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.emplogin.model.Employee;
import com.employee.emplogin.repository.EmployeeRepo;

@Service("empservice")
public class EmployeeService {
	@Autowired
	private EmployeeRepo emprepo;

	public String addInfoIntoExcel(Employee emp) {
		return emprepo.addInfoIntoExcel(emp);
	}

	public List<Employee> getAllInfo(Employee emp) {
		List<Employee> elist = new ArrayList<Employee>();
		elist = emprepo.getAllInfo(emp);
		return elist;
	}

}
