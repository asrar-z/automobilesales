package com.car.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {

	
	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private int officeid;
	private String role;
	private String salary;
	
}
