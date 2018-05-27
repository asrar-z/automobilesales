package com.car.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WHMInventoryController {

	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping("/inventory")
	public String showAddinventory()
	{
		return "WHMtemplates/inventory";
	}
}
