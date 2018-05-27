package com.car.entity;

import java.util.List;

import com.car.dto.EmployeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListEntity {

	private int draw;
	private int recordsTotal;
	private int recordsFiltered;

	List<EmployeeEntity> employees;
}
