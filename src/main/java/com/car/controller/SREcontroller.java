package com.car.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.MakeDao;
import com.car.dao.spec.ServiceDao;
import com.car.dao.spec.ServiceProblemsDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.MakeDto;
import com.car.dto.ServiceDto;
import com.car.entity.ServiceEntity;
import com.google.common.collect.Lists;

@Controller
public class SREcontroller {
@Autowired ServiceDao s;

@Autowired ServiceProblemsDao sp;
@Autowired MakeDao m;
@Autowired VehicleDao v;
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping("/servicesoftheday")
	public String showservices()
	{
		return "SEtemplates/servicesoftheday";
	}
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping("/servicesoftheday/get")
	@ResponseBody
	public List<ServiceEntity> getservices() throws IOException
	{
		List<ServiceDto> l= s.gettoday();
		List<ServiceEntity> entity=Lists.newArrayList();
		for(ServiceDto ll:l){
			String status="Pending";
			
			if(String.valueOf(ll.getCompleted()).equals("1"))
			{
				status="Completed";
			}
			else if(String.valueOf(ll.getCompleted()).equals("2"))
			{
				status="Cancelled";
			}
		String problem=sp.getby(ll.getProblem());
		MakeDto make=m.getBy(ll.getMake());
		String m=make.getName();
				Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
		String model=v.getname(ll.getModel(),year);
		ServiceEntity entity2=new ServiceEntity(ll.getApp_id(),ll.getId(), ll.getName(), ll.getNumber(),ll.getDate(),ll.getAddress() ,status,ll.getAssigned(),m,model,problem,ll.getTrack());
		entity.add(entity2);
		}
		return entity;
	}
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping("/servicesoftheday/updatetrack")
	@ResponseBody
	public void updatetrack(@RequestParam int app_id, @RequestParam String track) throws IOException
	{
		s.updatetrack(app_id, track);
	}
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping("/servicesoftheday/cancel")
	@ResponseBody
	public void cancel(@RequestParam int app_id) 
	{
		s.cancel(app_id);
	}
	
	
}

