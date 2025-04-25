package com.springproject.myproject.crmproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.springproject.myproject")
public class CrmprojectApplication {
	public static void main(String[] args) {
		SpringApplication.run(CrmprojectApplication.class, args);
	}
}


