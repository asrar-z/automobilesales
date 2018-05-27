package com.car.entity;


import com.car.dto.CustomersDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountEntity {

	public CustomerAccountEntity(CustomersDto account) {
		id=account.getUserid();
		firstname=account.getFirstName();
		lastname=account.getLastName();
		email=account.getEmail();
		if(account.getEmi()==1)
		emi="yes";
		else
			emi="no";
	}
	private String id;
	private String firstname;
	private String lastname;
	private String emi;
	private String email;
}
