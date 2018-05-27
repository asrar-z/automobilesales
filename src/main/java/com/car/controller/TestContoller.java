package com.car.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestContoller {

	
	@RequestMapping("/try")
	public String showtry()
	{
		return "/try";
	}
}
