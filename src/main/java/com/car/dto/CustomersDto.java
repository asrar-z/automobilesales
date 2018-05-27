package com.car.dto;

import com.car.entity.UserAccountCreationEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomersDto {

	private String userid;
	private String firstName;
	private String lastName;
	private String email;
	private byte emi;
	
	
	
	
	public  CustomersDto(UserAccountCreationEntity entity) {
		//logger.debug("inside reg/add" + entity.getUserid()+entity.getPassword()+entity.getFirstName()+entity.getLastName()+entity.getEmail());

		userid = entity.getUserid();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
	}
	
}
