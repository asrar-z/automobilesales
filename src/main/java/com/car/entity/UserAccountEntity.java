package com.car.entity;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.car.controller.LoginController;
import com.car.dto.CustomersDto;
import com.car.dto.UserAccountDto;

@NoArgsConstructor
@Data
public class UserAccountEntity implements Serializable {
	private static final Logger logger = Logger.getLogger(LoginController.class);
	private static final long serialVersionUID = -7395917071437157624L;

	private String userid;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	private int emi;
	private String searchParam;
	public UserAccountEntity(UserAccountDto userAccount, List<String> roles) {
		
		
		logger.debug(userAccount.getUserid()+userAccount.getPassword()+userAccount.getFirstName()+userAccount.getLastName()+userAccount.getEmail());
		
		this.userid = userAccount.getUserid();
		this.password = userAccount.getPassword();
		this.firstName = userAccount.getFirstName();
		this.lastName = userAccount.getLastName();
		this.email = userAccount.getEmail();
		this.roles = roles;
	}
	public UserAccountEntity(CustomersDto userAccount) {
		
		
		//logger.debug(userAccount.getUserid()+userAccount.getPassword()+userAccount.getFirstName()+userAccount.getLastName()+userAccount.getEmail());
		
		this.userid = userAccount.getUserid();
		this.firstName = userAccount.getFirstName();
		this.lastName = userAccount.getLastName();
		this.email = userAccount.getEmail();
		this.emi=userAccount.getEmi();
	}
}


