package com.car.dto;



import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Component
public class VehicleDto {

	
	private String id;
	private String name;
	private int make_id;
	private String cc;
	private String torque;
	private String speed;
	private String cost;

	private int sold;
	private byte[] img;
	private int quantity;
	String year;
	private String bp;
	private int category;
		
}
