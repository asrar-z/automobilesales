package com.car.entity;

import java.util.List;

import com.car.dto.ScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEntity2 {
List<ScheduleDto> todays;
}
