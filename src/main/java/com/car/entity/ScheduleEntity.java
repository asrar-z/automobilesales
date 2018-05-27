package com.car.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor




public class ScheduleEntity {
	private int app_id;
	private String id;
	private String name;
	private String number;
	private String date;
	private String status;
	private String assigned;
	private int office;
	
}
