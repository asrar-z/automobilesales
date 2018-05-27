package com.car.entity;

import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import com.car.dto.SMNotificationDto;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SMNotificationEntity {
private int count;
List<SMNotificationDto> notifications;
}
