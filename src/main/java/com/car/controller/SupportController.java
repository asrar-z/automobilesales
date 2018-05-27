package com.car.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SupportController {

	@PreAuthorize("hasAuthority('SUPPORT')")
	@RequestMapping("/support")
	public String showSupport()
	{
		return "SStemplates/support";
	}
}
