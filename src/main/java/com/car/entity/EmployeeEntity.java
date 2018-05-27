package com.car.entity;



import com.car.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeEntity {
	public EmployeeEntity(EmployeeDto e, String officename) {
		id=e.getId();
		firstname=e.getFirstname();
		lastname=e.getLastname();
		email=e.getEmail();
		office=officename;
		role=e.getRole();
		salary=e.getSalary();
	}
	private String id;
	private String firstname;
	private String lastname;
	private String email;
	private String office;
	private String role;
	private String salary;
}
