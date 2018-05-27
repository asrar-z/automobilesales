package com.car.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.entity.MakeEntity;
import com.car.entity.VehicleEntity;
import com.car.service.spec.MakeService;
import com.car.service.spec.VehicleService;


@Controller
public class CModelsController {

	
	@Autowired
	private MakeService makeService;
	@Autowired
	private VehicleService vehicleService;
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/models")
	public String showModels()
	{
		return "CStemplates/models";
	}
	
	
	@PreAuthorize("hasAnyAuthority('CUSTOMER','WAREHOUSEMANAGER')")
	@RequestMapping(value="/models/getMakes",method = RequestMethod.POST)
	@ResponseBody
	public MakeEntity getMakess()
	{
		return makeService.getAll();
	}
	
	
	@PreAuthorize("hasAnyAuthority('CUSTOMER','WAREHOUSEMANAGER')")
	@RequestMapping(value="/models/getVehicles",method = RequestMethod.POST)
	@ResponseBody
	public VehicleEntity getModelss(@RequestParam int make_id,@RequestParam int office)
	{
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
		return vehicleService.getAll(make_id,office,year);
	}
	
}
