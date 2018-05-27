package com.car.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SEFullPaymentController {
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/fullpaymentsale")
	public String showTodaysappointments()
	{
		return "SEtemplates/POSfull";
	}

}
