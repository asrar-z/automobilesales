package com.car.entity;

import java.util.List;

import com.car.dto.SaleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleListEntity {

	
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;

	List<SaleDto> sales;
}
