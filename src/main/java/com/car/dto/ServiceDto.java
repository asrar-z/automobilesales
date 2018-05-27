package com.car.dto;
import com.car.entity.ServiceCreationEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServiceDto {
	
	private int app_id;
	private String id;
	private String name;
	private String number;
	private String date;
	private String address;
	private byte completed;
	private String assigned;
	private int make;
	private String model;
	private int problem;
	private String track;
	
public ServiceDto(ServiceCreationEntity s)
{
	id=s.getId();
	name=s.getName();
	number=s.getNumber();
	date=s.getDate();
	address=s.getAddress();
	completed=0;
	make=s.getMake();
	model=s.getModel();
	problem=s.getProblem();
}
}
