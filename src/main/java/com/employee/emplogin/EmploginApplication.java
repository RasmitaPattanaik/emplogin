package com.employee.emplogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.employee.emplogin.controller.CreateEmp;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class EmploginApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmploginApplication.class, args);
	}
}
