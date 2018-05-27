package com.car.controller;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.spec.MakeDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.VehicleDto;


@Controller
public class WHMAddInventoryController {
@Autowired MakeDao makeDao;
@Autowired VehicleDto vDto;
@Autowired VehicleDao vDao;
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value="/addinventory",method = RequestMethod.GET)
	public String showAddinventory()
	{
		return "WHMtemplates/addinventory";
	}
	
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "addinventory/upload", method = RequestMethod.POST)
	@ResponseBody 
	    public void uploadFileHandler(@RequestParam("name") String name,@RequestParam("file") MultipartFile file) {
	 System.out.println("here");
	 
	        if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                System.out.println("Name:"+name+"\nFileb64:"+bytes);
	                
	                makeDao.insert(name, bytes);
	             
	            } catch (Exception e) {
	              
	            }
	        } 
	    }
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "addinventory/uploadv", method = RequestMethod.POST)
	@ResponseBody 
	    public void uploadVehicle(@RequestParam int office,@RequestParam("name2") String name,
	    		@RequestParam("vid") String id,
	    		@RequestParam("cc") String cc,
	    		@RequestParam("torque") String torque,
	    		@RequestParam("speed") String speed,
	    		@RequestParam("cost") String cost, 
	    		@RequestParam("quantity") int quantity,
	    		@RequestParam("file") MultipartFile file,
	    		@RequestParam("makeid") int make_id,
	    		@RequestParam("bp") String bp) {
	 System.out.println("here");
	 
	        if (!file.isEmpty()) {
	            try {
	                byte[] filebytes = file.getBytes();
	                System.out.println("Name:"+name+"\nFileb64:"+filebytes);
	                int sold=0;
	    Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
		int cat=1;
		long price=Long.parseLong(cost.replace("$", "").replace(",","").replace("Rs", ""));
		if(office==2)
		{
			if(price<=2000000)
				cat=1;
			else if(price<=3500000)
				cat=2;
			else
				cat=3;
		}
		else
		{
			if(price<=200000)
				cat=1;
			else if(price<=140000)
				cat=2;
			else
				cat=3;
			
		}
	                vDto= new VehicleDto(id, name, make_id, cc, torque, speed, cost, sold, filebytes, quantity,year,bp,cat);
	                vDao.insert(office,vDto);
	            } catch (Exception e) {
	              
	            }
	        } 
	    }
	
}
