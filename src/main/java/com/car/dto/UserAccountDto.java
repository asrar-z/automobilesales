package com.car.dto;

import org.apache.log4j.Logger;

import com.car.controller.LoginController;
import com.car.entity.UserAccountCreationEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAccountDto {
	private static final Logger logger = Logger.getLogger(LoginController.class);

	private String userid;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int roleid;
	
	public UserAccountDto(UserAccountCreationEntity entity) {
		logger.debug("inside reg/add" + entity.getUserid()+entity.getPassword()+entity.getFirstName()+entity.getLastName()+entity.getEmail());

		userid = entity.getUserid();

			password = entity.getPassword();
	
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();
		roleid=entity.getRoleId();
	}
}
