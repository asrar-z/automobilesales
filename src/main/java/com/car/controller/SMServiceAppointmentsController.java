package com.car.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.EmployeeDao;
import com.car.dao.spec.ServiceDao;
import com.car.dto.EmployeeDto;
import com.car.entity.ScheduleFetchEntity;
import com.car.entity.ScheduleListEntity;
import com.car.entity.ServiceFetchEntity;
import com.car.entity.ServiceListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ServiceService;


@Controller
public class SMServiceAppointmentsController {
	
	@Autowired
	ServiceService serviceservice;
	@Autowired
	ServiceDao serviceDao;
	@Autowired EmployeeDao employeeDao;
	
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/serviceappointments")
public String showCustomerList()
{
	return "SMtemplates/serviceappointments";
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "serviceappointments/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ServiceListEntity findApp(@RequestBody ServiceFetchEntity entity) throws ServiceException, ParseException, IOException {

		return serviceservice.getAll(entity);

}


@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "serviceappointments/update", method = RequestMethod.PUT)
@ResponseBody
public void update(@RequestParam Timestamp t, @RequestParam String id) throws IOException
{
	serviceDao.update(t, id);
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="salesappointments/getsre")
@ResponseBody
public List<TodaysScheduleEntity> getSRE() throws IOException

{
	List<TodaysScheduleEntity> ses=new ArrayList<TodaysScheduleEntity>();
	List<EmployeeDto> employees= employeeDao.getSRE();
	for(EmployeeDto e:employees){
		int count =serviceDao.getSRECount(e.getId());
	TodaysScheduleEntity td=new TodaysScheduleEntity(e.getId(), e.getFirstname(),count);
	ses.add(td);
	}
	return ses;
	
}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "serviceappointments/updateSRE", method = RequestMethod.PUT)
@ResponseBody
public void updatesre(@RequestParam int app_id, @RequestParam String sre_id) throws IOException
{
	serviceDao.updateSRE(app_id, sre_id);
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "serviceappointments/updatetrack", method = RequestMethod.PUT)
@ResponseBody
public void updatetrack(@RequestParam int app_id, @RequestParam String track) throws IOException
{
	serviceDao.updatetrack(app_id, track);
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "serviceappointments/update2", method = RequestMethod.PUT)
@ResponseBody
public void update(@RequestParam int app_id) throws IOException
{
	serviceDao.update(app_id);
}

}
