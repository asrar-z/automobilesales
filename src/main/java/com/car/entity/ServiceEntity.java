package com.car.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEntity {
	private int app_id;
	private String id;
	private String name;
	private String number;
	private String date;
	private String address;
	private String status;
	private String assigned;
	private String make;
	private String model;
	private String problem;
	private String track;
}
