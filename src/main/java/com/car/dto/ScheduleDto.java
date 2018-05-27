package com.car.dto;



import com.car.entity.ScheduleCreationEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ScheduleDto {
	private int app_id;
	private String id;
	private String name;
	private String number;
	private String date;
	private byte completed;
	private String assigned;
	private int officeid;
	
	public ScheduleDto(ScheduleCreationEntity s)
	{
		id=s.getUserid();
		name=s.getName();
		number=s.getNumber();
		date=s.getDate();
		completed=0;
		officeid=s.getOfficeid();
	}
	
}
