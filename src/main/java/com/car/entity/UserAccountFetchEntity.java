package com.car.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserAccountFetchEntity {
	private int draw;
	private int start;
	private int length;
	private int roleId;
	private String searchParam;
}
