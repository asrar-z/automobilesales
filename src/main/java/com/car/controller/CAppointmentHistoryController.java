package com.car.controller;

import java.io.IOException;
import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.entity.ScheduleFetchEntity;
import com.car.entity.ScheduleListEntity;
import com.car.entity.ServiceFetchEntity;
import com.car.entity.ServiceListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ScheduleService;
import com.car.service.spec.ServiceService;



@Controller
public class CAppointmentHistoryController{
	
	
	@Autowired
	ScheduleService scheduleservice;
	
	@Autowired
	ServiceService serviceservice;
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/appointmenthistory")
	public String showAppointmenthistory()
	{
		return "CStemplates/appointmenthistory";
	}

	
	
	
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value = "appointmenthistory/getSaleApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ScheduleListEntity findApp(@RequestBody ScheduleFetchEntity entity) throws ServiceException, ParseException {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); 
			return scheduleservice.getBy(entity,name);

	}
	
	
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value = "appointmenthistory/getServiceApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ServiceListEntity findApp(@RequestBody ServiceFetchEntity entity) throws ServiceException, IOException {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); 
			return serviceservice.getBy(entity,name);

	}
	
}
