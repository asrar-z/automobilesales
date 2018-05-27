package com.car.entity;

import java.util.List;

import com.car.dto.EMIDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor




public class EMIEntity {

	List<EMIDto> emis;
}
