package com.employee.emplogin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.employee.emplogin.model.Admin;
import com.employee.emplogin.model.Employee;
import com.employee.emplogin.service.AdminService;

@TestConfiguration
public class AdminControllerTest extends AbstractTest {

	@InjectMocks
	private AdminController adminController;
	@Mock
	private AdminService adminService;

	@Before
	@Override
	public void setUp() {
		super.setUp();
	}

	@Test
	public void addAdminInfoTest() {
		Admin admin = new Admin();
		admin.setAdminId("111");
		admin.setAdminPwd("111");
		when(adminService.addAdminIntoExcel(admin)).thenReturn(Mockito.anyString());
		ResponseEntity<String> response = adminController.addAdminInfo(admin);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void identifyAdminUserTest() {
		String adminId = "111";
		when(adminService.identifyAdminUser(adminId)).thenReturn(Mockito.anyString());
		ResponseEntity<String> response = adminController.identifyAdminUser(adminId);
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}

	@Test
	public void addAdminInfoWithCondNullTest() {
		String admId = "1";
		String empId = "111";
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		assertEquals(HttpStatus.NOT_FOUND.value(), adminRes.getStatusCodeValue());
		when(adminService.delByAdmin(empId)).thenReturn(Mockito.anyString());
		adminController.addAdminInfoWithCond(admId, empId);
	}

	@Test
	public void addAdminInfoWithCondValidTest() {
		String admId = "2";
		String empId = "111";
		when(adminService.identifyAdminUser(admId)).thenReturn("2");
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		int res = adminRes.getStatusCodeValue();
		assertEquals(res, HttpStatus.OK.value());
		when(adminService.delByAdmin(empId)).thenReturn("111");
		adminController.addAdminInfoWithCond(admId, empId);
	}

	@Test
	public void addCondValidTestEmpIdNull() {
		String admId = "2";
		String empId = null;
		when(adminService.identifyAdminUser(admId)).thenReturn("2");
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		int res = adminRes.getStatusCodeValue();
		assertEquals(res, HttpStatus.OK.value());
		when(adminService.delByAdmin(empId)).thenReturn(null);
		adminController.addAdminInfoWithCond(admId, empId);
	}

	@Test
	public void readByadminTestValid() {
		String admId = "2";
		Employee e1 = new Employee();
		e1.setEmpId("1");
		e1.setPassword("1");
		Employee e2 = new Employee();
		e2.setEmpId("2");
		e2.setPassword("2");
		List<Employee> empList = new ArrayList<Employee>();
		empList.add(e1);
		empList.add(e2);
		when(adminService.identifyAdminUser(admId)).thenReturn("2");
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		int res = adminRes.getStatusCodeValue();
		assertEquals(res, HttpStatus.OK.value());
		when(adminService.readByAdmin()).thenReturn(empList);
		adminController.readByadmin(admId);
	}
	@Test
	public void readByadminTestInValid() {
		String admId = "2";
		//Employee e1 = new Employee();
		List<Employee> empList = new ArrayList<Employee>();
		//empList.add(e1);
		when(adminService.identifyAdminUser(admId)).thenReturn("2");
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		int res = adminRes.getStatusCodeValue();
		assertEquals(res, HttpStatus.OK.value());
		when(adminService.readByAdmin()).thenReturn(empList);
		adminController.readByadmin(admId);
	}
	
	@Test
	public void readByadminInValidCred() {
		String admId = "3";
		//Employee e1 = new Employee();
		List<Employee> empList = new ArrayList<Employee>();
		//empList.add(e1);
		//when(adminService.identifyAdminUser(admId)).thenReturn("3");
		ResponseEntity<String> adminRes = adminController.identifyAdminUser(admId);
		int res = adminRes.getStatusCodeValue();
		assertEquals(res, HttpStatus.NOT_FOUND.value());
		when(adminService.readByAdmin()).thenReturn(empList);
		adminController.readByadmin(admId);
	}
	
}
