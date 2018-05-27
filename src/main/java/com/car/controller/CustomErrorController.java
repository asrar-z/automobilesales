package com.car.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{

@RequestMapping("/error")
public String errorPage()
{
	return "unauthorized";
}

@Override
public String getErrorPath() {
	
	return "/test/error";
}
	
}
