package com.car.controller;

import java.io.IOException;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.car.dao.impl.WHMNotificationDaoImpl;
import com.car.dao.spec.MakeDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.VehicleDto;
import com.car.dto.WHMnotificationDto;
import com.car.entity.SMNotificationEntity;
import com.car.entity.WHMNotificationEntity;
import com.car.service.spec.OfficeService;


@Controller
public class WHMUpdateInventoryController {
	@Autowired VehicleDto vDto;
	@Autowired MakeDao makeDao;
	@Autowired VehicleDao vDao;
	@Autowired WHMNotificationDaoImpl whmnotDao;
	@Autowired OfficeService off;
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping("/updateinventory")
	public String showAddinventory()
	{
		return "WHMtemplates/updatedetails";
	}
	
	
	
	
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "updateinventory/update", method = RequestMethod.POST)
	@ResponseBody 
	    public void uploadFileHandler(@RequestParam("makeid") String makeid,@RequestParam("name") String name,@RequestParam("file") MultipartFile file) throws IOException {
	 System.out.println("here");
	 int id=Integer.parseInt(makeid);
	        if (!file.isEmpty()) {
	            try {
	                byte[] bytes = file.getBytes();
	                System.out.println("ID:"+id+"Name:"+name+"\nFileb64:"+bytes);
	                
	                makeDao.update(name, bytes, id);
	             
	            } catch (Exception e) {
	              
	            }
	        }
	        else
	        {
	        	makeDao.update_without_logo(name, id);
	        }
	    }
	
	
	
	@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
	@RequestMapping(value = "updateinventory/updatev", method = RequestMethod.POST)
	@ResponseBody 
	    public void uploadVehicle(
	    		@RequestParam int office,
	    		@RequestParam("name2") String name,
	    		@RequestParam("vid") String id,
	    		@RequestParam("cc") String cc,
	    		@RequestParam("torque") String torque,
	    		@RequestParam("speed") String speed,
	    		@RequestParam("cost") String cost,
	    		@RequestParam("bp") String bp, 
	    		@RequestParam("quantity") int quantity,
	    		@RequestParam("file") MultipartFile file,
	    		@RequestParam("makeid")int make_id,
	    		@RequestParam("oldid") String oldid) throws IOException
	{
	 System.out.println("here");
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
	        if (!file.isEmpty()) {
	            try {
	                byte[] filebytes = file.getBytes();
	                System.out.println("Name:"+name+"\nFileb64:"+filebytes+"new torque : "+ torque );
	              
	               		
	                vDto= new VehicleDto(id, name, make_id, cc, torque, speed, cost, 0, filebytes, quantity,year,bp,cat);
	                vDao.insert(vDto,oldid,office);
	            } catch (Exception e) {
	              
	            }
	        } 
	        else
	        {
	        	vDto= new VehicleDto(id, name, make_id, cc, torque, speed, cost, 0, null, quantity,year,bp,cat);
	        	vDao.insert_without_pic(vDto,oldid,office);
	        }
	    }
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value = "updateinventory/pricenotification", method = RequestMethod.POST)
	@ResponseBody 
	public void updateprice(@RequestParam String name,@RequestParam String old,@RequestParam String new_price,@RequestParam int office) throws IOException
	{
		String ofname=off.getBy(office);
		String subject="Price Update";
		String message="Update Old Price: <b>"+old+"</b><p> New price: <b>"+new_price+"</b></p> for Outlet "+ofname;
		whmnotDao.notify(new WHMnotificationDto(name, subject, message));
	}
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value = "updateinventory/stocknotification", method = RequestMethod.POST)
	@ResponseBody 
	public void updatestock(@RequestParam String name,@RequestParam int quantity,@RequestParam int office) throws IOException
	{
		String ofname=off.getBy(office);
		String subject="Stock update";
		String message="Increase Stock of: <b>"+name+"</b><p> by: <b>"+quantity+"</b></p> for Outlet "+ofname;
		whmnotDao.notify(new WHMnotificationDto(name, subject, message));
	}	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value = "updateinventory/newstocknotification", method = RequestMethod.POST)
	@ResponseBody 
	public void newtock(@RequestParam String name,@RequestParam int office) throws IOException
	{
		String ofname=off.getBy(office);
		String subject="New Stock";
		String message="Add new Make <b>"+name+"</b></p> for Outlet "+ofname;
		whmnotDao.notify(new WHMnotificationDto(name, subject, message));
	}	

@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
@RequestMapping(value="/updateinventory/notifications")
@ResponseBody
public WHMNotificationEntity poll() throws IOException{
	return whmnotDao.get();
}
@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
@RequestMapping(value="/updateinventory/getc")
@ResponseBody
public int prevC() throws IOException{
	return whmnotDao.getprevC();
}
@PreAuthorize("hasAuthority('WAREHOUSEMANAGER')")
@RequestMapping(value="/updateinventory/updatec")
@ResponseBody
public void prevC(@RequestParam int count) throws IOException{
	whmnotDao.updateprev(count);
}
	
}
