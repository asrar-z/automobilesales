package com.car.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.MakeDao;
import com.car.dao.spec.VehicleDao;


@Controller
public class WHMRemoveInventoryController {
@Autowired VehicleDao vehicleDao;
@Autowired MakeDao makeDao;
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping("/removeinventory")
	public String showAddinventory()
	{
		return "WHMtemplates/removeinventory";
	}
	
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "/removeinventory", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deletemake(@RequestParam int id) throws IOException {
		// return false if any user in this office or id does not exist
		
		return makeDao.deleteBy(id);
	}
	
	
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "/removeinventory/v", method = RequestMethod.DELETE)
	@ResponseBody
	public boolean deletev(@RequestParam String id,@RequestParam int office) throws IOException {
		// return false if any user in this office or id does not exist
		
		return vehicleDao.delete(id,office);
	}
	
}

