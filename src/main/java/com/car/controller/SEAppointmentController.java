package com.car.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.ScheduleDao;
import com.car.dto.ScheduleDto;
import com.car.entity.ScheduleEntity2;


@Controller
public class SEAppointmentController {
	
	@Autowired ScheduleDao scheduleDao;
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/appointmentsoftheday")
	public String showTodaysappointments()
	{
		return "SEtemplates/AppointmentOfTheDay";
	}
	
	
	@PreAuthorize("hasAuthority('SALESEXECUTIVE')")
	@RequestMapping(value ="/appointmentsoftheday/get",method = RequestMethod.POST)
	@ResponseBody
	public ScheduleEntity2 get() throws IOException
	{
		return new ScheduleEntity2(scheduleDao.gettoday());
	}
	

}
