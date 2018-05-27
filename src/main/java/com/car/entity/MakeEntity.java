package com.car.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.car.dto.MakeDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeEntity {
	
private List<MakeDto> makes;

}
