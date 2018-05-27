package com.car.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
public class LoginController {

	//private static final Logger logger = Logger.getLogger(LoginController.class);
	@RequestMapping(value = "/login")
	public String login() throws IOException {

		return "login";
}
}
