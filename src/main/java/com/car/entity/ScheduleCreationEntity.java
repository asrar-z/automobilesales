package com.car.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreationEntity {

	private String userid;
	private String name;
	private String number;
	private String date;
	private int officeid;
	/*public ScheduleCreationEntity(String name,String number,Timestamp date)
	{
		this.name=name;
		this.number=number;
		this.date=date;
	}*/
}
