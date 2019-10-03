package com.employee.emplogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.emplogin.model.Admin;
import com.employee.emplogin.model.Employee;
import com.employee.emplogin.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/addData")
	public ResponseEntity<String> addAdminInfo(@RequestBody Admin adm) {
		return new ResponseEntity<>(adminService.addAdminIntoExcel(adm), HttpStatus.OK);
	}

	@GetMapping("/{adminId}")
	public ResponseEntity<String> identifyAdminUser(@PathVariable String adminId) {
		String response = adminService.identifyAdminUser(adminId);
		if (response == null) {
			return new ResponseEntity<String>(adminId, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<String>(adminId, HttpStatus.OK);
		}
	}

	@DeleteMapping("/{admId}/{empId}")
	public ResponseEntity<String> addAdminInfoWithCond(@PathVariable String admId, @PathVariable String empId) {
		ResponseEntity<String> adminRes = identifyAdminUser(admId);
		String msg = null;
		HttpStatus status = null;
		if (adminRes.getStatusCodeValue() == HttpStatus.OK.value()) {
			String emplId = adminService.delByAdmin(empId);
			if (emplId != null) {
				msg = "Employee deleted successfully.";
				status = HttpStatus.OK;
			} else {
				msg = "Unable to delete.";
				status = HttpStatus.NOT_FOUND;
			}
		} else {
			msg = "Invalid credential";
			status = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<String>(msg, status);
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@GetMapping("/aa/{admId}")
	public ResponseEntity<Object> readByadmin(@PathVariable String admId) {
		ResponseEntity<String> adminRes = identifyAdminUser(admId);
		String msg = null;
		HttpStatus status = null;
		List<Employee> empList = null;
		if (adminRes.getStatusCodeValue() == HttpStatus.OK.value()) {
			empList = adminService.readByAdmin();
			if (!empList.isEmpty()) {
				return new ResponseEntity<Object>(empList, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(empList, HttpStatus.NO_CONTENT);
			}
		} else {
			return new ResponseEntity("Invalid credential", HttpStatus.NOT_FOUND);
		}

	}
}
