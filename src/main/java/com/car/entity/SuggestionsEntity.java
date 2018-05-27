package com.car.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionsEntity {
	private String id;
	private String name;
	private String totalrevenue;
	private String investment;
	private String tax;
	private String netprofit;
	private String margin;
	private int category;

}
