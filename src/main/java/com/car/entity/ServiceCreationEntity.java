package com.car.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCreationEntity {
	private String id;
	private String name;
	private String number;
	private String date;
	private String address;
	private int make;
	private String model;
	private int problem;
}
