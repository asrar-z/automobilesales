package com.car.controller;


import java.text.ParseException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.entity.ScheduleCreationEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ScheduleService;


@Controller
public class CScheduleController{
	
	@Autowired
	ScheduleService scheduleService;
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/schedule")
	public String showSchedule ()
	{
		return "CStemplates/schedule";
	}

	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="schedule/add", method = RequestMethod.POST)
	@ResponseBody
	public void shedule (@RequestBody ScheduleCreationEntity s) throws ServiceException, ParseException
	{
		System.out.println(s.getName()+s.getNumber()+s.getUserid()+s.getDate());
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); 
		s.setUserid(name);
		scheduleService.insert(s);

	}
	
	
}
