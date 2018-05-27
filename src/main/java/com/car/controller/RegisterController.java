package com.car.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.entity.UserAccountCreationEntity;
import com.car.service.spec.UserService;


@Controller
public class RegisterController {
	
	
	private static final Logger logger = Logger.getLogger(LoginController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/register")
	public String showRegForm()
	{
		return "/register";
	}
	
	

	@RequestMapping(value = "/register/addUserAccount", method = RequestMethod.POST)
	@ResponseBody
	public void addUserAccount(@RequestBody UserAccountCreationEntity userAccountCreationEntity) {
		logger.debug("inside reg/add" + userAccountCreationEntity.getUserid()+userAccountCreationEntity.getPassword()+userAccountCreationEntity.getFirstName()+userAccountCreationEntity.getLastName()+userAccountCreationEntity.getEmail());
		userAccountCreationEntity.setRoleId(3);
		userService.insert(userAccountCreationEntity);
	}
	
}
