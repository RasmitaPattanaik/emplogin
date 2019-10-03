package com.employee.emplogin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.emplogin.model.Employee;
import com.employee.emplogin.service.EmployeeService;

@RestController
@RequestMapping("/empController")
public class CreateEmp {

	@Autowired
	private EmployeeService empservice;

	@PostMapping("/employees")
	public ResponseEntity<String> addEmplInfo(@RequestBody Employee emp) {
		// return employeeService.addInfoIntoExcel(emp);
		return new ResponseEntity<>(empservice.addInfoIntoExcel(emp), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllEmpinfo", produces = { "application/json" })
	public ResponseEntity<List<Employee>> getAllEmpInfo(@RequestParam String AdminId, @RequestParam String AdminPwd) {
		List<Employee> emplist = null;
		if (AdminId.equals("1") && AdminPwd.equals("1")) {
			emplist = empservice.getAllInfo(new Employee());
			if (emplist.isEmpty()) {
				return new ResponseEntity<List<Employee>>(HttpStatus.NOT_FOUND);
			}
		} else if (!(AdminId.equals("1") && AdminPwd.equals("1"))) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<List<Employee>>(emplist, HttpStatus.OK);
	}
}
