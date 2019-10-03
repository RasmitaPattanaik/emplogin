package com.employee.emplogin.controller.ft;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import com.employee.emplogin.model.Admin;
import com.employee.emplogin.model.Employee;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminTest {

	@Test
	public void addAdminInfoTest() throws Exception {
		String url = "http://localhost:9090/admin/addData";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Admin adm = new Admin();
		adm.setAdminId("1");
		adm.setAdminPwd("1");
		String ipJson = mapToJson(adm);
		HttpEntity<String> request = new HttpEntity<String>(ipJson, headers);
		String resul = restTemplate.postForObject(url, request, String.class);
		assertEquals(true,
				resul.contains("Congrats!!! Admin " + adm.getAdminId() + " You have registered successfully"));
	}

	@Test
	public void identifyAdminUserTestElse() throws Exception {
		String url = "http://localhost:9090/admin/1";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Admin adm = new Admin();
		adm.setAdminId("1");
		String ipJson = mapToJson(adm);
		HttpEntity<String> request = new HttpEntity<String>(ipJson, headers);
		String resul = restTemplate.getForObject(url, String.class, request);
		assertEquals(true, resul.contains("1"));
	}

	@Test(priority=1)
	public void addEmplTest()throws Exception{
		String url = "http://localhost:9090/empController/employees";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		Employee emp=new Employee();
		emp.setEmpId("2");
		emp.setPassword("2");
		String ipJson = mapToJson(emp);
		HttpEntity<String> request = new HttpEntity<String>(ipJson, header);
		String resul = restTemplate.postForObject(url, request, String.class);
		assertEquals(true, resul.contains("Congrats!!!" + emp.getEmpId() + "You have registered successfully"));
	}
	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void addAdminInfoWithCondTest() throws Exception {
		boolean success = false;
		String url = "http://localhost:9090/admin/{admId}/{empId}";
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		Map<String, String> params = new HashMap<String, String>();
		params.put("admId", "1");
		params.put("empId", "2");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(url, params);
		success = true;
		assertEquals(true, success);
		/*url = "http://localhost:9090/admin/aa/1";
		List<Employee> list=new ArrayList<>();
		Employee e=new Employee();
		e.setEmpId("1");
		e.setPassword("1");
		list.add(e);
		Employee e1=new Employee();
		e1.setEmpId("3");
		e1.setPassword("3");
		list.add(e1);
		
		Employee e2=new Employee();
		e2.setEmpId("4");
		e2.setPassword("4");
		list.add(e2);
		
		header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		restTemplate = new RestTemplate();
		String ipJson = mapToJson(list);
		HttpEntity<String> request = new HttpEntity<String>(ipJson, header);
		ResponseEntity<Object> resul = restTemplate.getForObject(url, ResponseEntity.class, request);
		assertEquals(list, resul);*/
	}
	
	

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
}
