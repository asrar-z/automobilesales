package com.car.entity;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.car.dto.UserAccountDto;

@NoArgsConstructor
@Data
public class UserAccountCreationEntity {
	private String userid;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private List<String> roles;
	public int roleId;
	
	public UserAccountCreationEntity(UserAccountDto userAccount, List<String> roles, int roleId) {
		this.userid = userAccount.getUserid();
		this.password = userAccount.getPassword();
		this.firstName = userAccount.getFirstName();
		this.lastName = userAccount.getLastName();
		this.email = userAccount.getEmail();
		this.roles = roles;

			this.roleId=roleId;
		
	}
}
