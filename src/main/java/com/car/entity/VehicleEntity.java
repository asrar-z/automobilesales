package com.car.entity;

import java.util.List;

import com.car.dto.VehicleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleEntity {

	List<VehicleDto> vehicles;
}
