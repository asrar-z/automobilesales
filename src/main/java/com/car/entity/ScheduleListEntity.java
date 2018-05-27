package com.car.entity;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleListEntity {
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	List<ScheduleEntity> schedules;
}
