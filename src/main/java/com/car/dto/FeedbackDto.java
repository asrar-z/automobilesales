package com.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class FeedbackDto {

	
	private String firstname;
	private String lastname;
	private String email;
	private String subject;
	private String message;
	
	
}
