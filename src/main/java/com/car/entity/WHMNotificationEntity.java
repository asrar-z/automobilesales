package com.car.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.car.dto.WHMnotificationDto;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class WHMNotificationEntity {
	private int count;
	List<WHMnotificationDto> notifications;
}
