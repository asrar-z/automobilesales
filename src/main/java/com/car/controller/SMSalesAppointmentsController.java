package com.car.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.EmployeeDao;
import com.car.dao.spec.ScheduleDao;
import com.car.dao.spec.ServiceDao;
import com.car.dto.EmployeeDto;
import com.car.entity.EmployeeEntity;
import com.car.entity.ScheduleFetchEntity;
import com.car.entity.ScheduleListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ScheduleService;


@Controller
public class SMSalesAppointmentsController{
	
	@Autowired
	ScheduleService scheduleservice;
	@Autowired
	ScheduleDao scheduleDao;
	@Autowired ServiceDao serviceDao;
	@Autowired EmployeeDao employeeDao;
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/salesappointments")
public String showCustomerList()
{
	return "SMtemplates/salesappointments";
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "salesappointments/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ScheduleListEntity findApp(@RequestBody ScheduleFetchEntity entity) throws ServiceException, ParseException {

		return scheduleservice.getAll(entity);

}


@PreAuthorize("hasAnyAuthority('SALESMANAGER','SALESEXECUTIVE')")
@RequestMapping(value = "salesappointments/update", method = RequestMethod.PUT)
@ResponseBody
public void update(@RequestParam int id) throws IOException
{
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();;
	Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	System.out.println("\n"+authorities);
	if(authorities.toString().contains("SALESMANAGER"))
		{
		
		System.out.println("Contains SM");
		scheduleDao.update(id,new Byte("1"));
		}
	else
		{
		System.out.println(authorities);
		scheduleDao.update(id,new Byte("2"));
		}
}


@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "salesappointments/updateSE", method = RequestMethod.PUT)
@ResponseBody
public void update(@RequestParam int app_id,@RequestParam String se_id) throws IOException
{
	scheduleDao.updateSE(app_id,se_id);
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="salesappointments/getse")
@ResponseBody
public List<TodaysScheduleEntity> getSE(@RequestParam int id,@RequestParam int type) throws IOException

{
	List<TodaysScheduleEntity> ses=new ArrayList<TodaysScheduleEntity>();
	List<EmployeeDto> employees= employeeDao.getSE(id);
	System.out.print("debug"+employees);
	for(EmployeeDto e:employees){
		int count=scheduleDao.getSECount(e.getId());
		if(type==2)
		 count =serviceDao.getSRECount(e.getId());
		
	TodaysScheduleEntity td=new TodaysScheduleEntity(e.getId(), e.getFirstname(),count);
	ses.add(td);
	}
	return ses;
	
}


}
