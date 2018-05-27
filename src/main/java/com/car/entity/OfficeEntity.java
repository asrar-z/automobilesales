package com.car.entity;

import java.util.List;

import com.car.dto.OfficeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfficeEntity {
	public OfficeEntity(List<OfficeDto> list) {
		offices=list;
	}
	private int draw;
	private int recordsTotal;
	private int recordsFiltered;
	private List<OfficeDto> offices;
}
