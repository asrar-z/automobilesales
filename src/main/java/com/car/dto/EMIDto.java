package com.car.dto;
import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EMIDto {

	public EMIDto(int int1, String string, String string2, String string3,
			String string4, String string5, String string6, int int2,
			String string7,String mail,int prev) {
		id=int1;
		c_id=string;
		firstname=string2;
		lastname=string3;
		total=string4;
		duration=string5;
		rate=string6;
		office=int2;
		status=Arrays.asList(string7.toString().replace("[", "").replace("]","").replace(" ","").split(","));
		email=mail;
		month=prev;
	}
	private int id;
	private String c_id;
	private String firstname;
	private String lastname; 
	private String total;
	private String duration;
	private String rate;
	private int office;
	private List<String> status;
	private String email;
	private int month;
	
}
