package com.employee.emplogin.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.employee.emplogin.model.Employee;
import com.employee.emplogin.service.EmployeeService;

public class CreateEmpTest extends AbstractTest {
	@InjectMocks
	private CreateEmp createEmp;
	@Mock
	private EmployeeService empservice;
//	@Spy
//	private List<Employee> parts = new ArrayList<>();

	@Test
	public void addEmplInfoTest() {
		Employee emp = new Employee();
		emp.setEmpId("111");
		emp.setPassword("111");
		when(empservice.addInfoIntoExcel(emp)).thenReturn("111");
		ResponseEntity<String> response = createEmp.addEmplInfo(emp);
		int code = response.getStatusCodeValue();
		assertEquals(code, HttpStatus.OK.value());
		createEmp.addEmplInfo(emp);
	}

	@Test
	public void getAllEmpInfoTest() {
		String AdminId = "1";
		String AdminPwd = "1";
		List<Employee> emplist = new ArrayList<Employee>();
		Employee emp = new Employee();
		when(empservice.getAllInfo(emp)).thenReturn(emplist);
		createEmp.getAllEmpInfo(AdminId, AdminPwd);
	}

	@Test
	public void getAllEmpEmptyInfoTest() {
		String AdminId = "3";
		String AdminPwd = "2";
		Employee emp = new Employee();
		List<Employee> emplist;
		ResponseEntity<List<Employee>> response = createEmp.getAllEmpInfo(AdminId, AdminPwd);
		int code = response.getStatusCodeValue();
		assertEquals(code, HttpStatus.NOT_ACCEPTABLE.value());
		createEmp.getAllEmpInfo(AdminId, AdminPwd);
	}

	@Test
	public void getAllEmpEmptyIfTest() {
		String AdminId = "5";
		String AdminPwd = "6";
		Employee emp = new Employee();
		/*
		 * emp.setEmpId(null); emp.setPassword(null);
		 */
		/*
		 * ArrayList<Employee> list = new ArrayList<>(); list.add(emp);
		 */
		List<Employee> emptyList = Collections.emptyList();
		when(empservice.getAllInfo(emp)).thenReturn(emptyList);
		createEmp.getAllEmpInfo(AdminId, AdminPwd);
	}

	@Test
	public void test() {
		String AdminId = "1";
		String AdminPwd = "1";
		
		 List<Employee> emplist=new ArrayList<>();
		 /* Employee(); e1.setEmpId("777"); e1.setPassword("777");
		 * emplist.add(e1);
		 * when(empservice.getAllInfo(emp)).thenReturn(emplist);
		 */
		Employee e1 = new Employee();
		e1.setEmpId("777");
		e1.setPassword("777");
		emplist.add(e1);
		when(empservice.getAllInfo(Mockito.any(Employee.class))).thenReturn(emplist);
		createEmp.getAllEmpInfo(AdminId, AdminPwd);
	}
}
