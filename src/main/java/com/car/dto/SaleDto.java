package com.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDto {
	private int saleid;
	private String id;
	private String customer_firstname;
	private String customer_lastname;
	private String vehicle_id;
	private int emi;
	private String total_amount;
	private int officeid;
	private String profit;
	private String year;

}
