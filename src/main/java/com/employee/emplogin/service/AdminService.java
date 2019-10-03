package com.employee.emplogin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.emplogin.model.Admin;
import com.employee.emplogin.model.Employee;
import com.employee.emplogin.repository.AdminRepo;

@Service("adminService")
public class AdminService {
	@Autowired
	private AdminRepo adminRepo;

	public String addAdminIntoExcel(Admin ad) {
		return adminRepo.addInfoIntoExcel(ad);
	}

	public String identifyAdminUser(String admId) {
		return adminRepo.identifyAdminUser(admId);
	}

	public String delByAdmin(String EmpId) {
		return adminRepo.delByAdmin(EmpId);
	}

	public List<Employee> readByAdmin() {
		List<Employee> empList = adminRepo.readbyAdmin();
		return empList;
	}
}
