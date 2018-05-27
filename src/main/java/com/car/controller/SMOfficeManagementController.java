package com.car.controller;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.EmployeeDao;
import com.car.dao.spec.OfficeDao;
import com.car.dao.spec.SMNotificationDao;
import com.car.dto.EmployeeDto;
import com.car.dto.OfficeDto;
import com.car.dto.SMNotificationDto;
import com.car.entity.EMIEntity;
import com.car.entity.EmployeeEntity;
import com.car.entity.EmployeeListEntity;
import com.car.entity.OfficeEntity;



@Controller
public class SMOfficeManagementController {
	
	
	@Autowired OfficeDao officeService;
	@Autowired EmployeeDao employeeDao;
	@Autowired SMNotificationDao s;
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/office")
public String showCustomerList()
{
	return "SMtemplates/office-management";
}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/notification")
public String showpanel()
{
	return "SMtemplates/notifications";
}
@PreAuthorize("hasAnyAuthority('SALESMANAGER')")
@RequestMapping(value = "office/get")
@ResponseBody
public OfficeEntity getOffices() throws IOException {
	System.out.println(officeService.get());
	return new OfficeEntity(officeService.get());
}

@PreAuthorize("hasAnyAuthority('SALESMANAGER')")
@RequestMapping(value = "employees/getall")
@ResponseBody
public List<EmployeeDto> getemployees(@RequestParam int officeid) throws IOException {
	System.out.println(employeeDao.get(officeid));
	return  (employeeDao.get(officeid));
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="office/getnot")
@ResponseBody
public NotEntity getnot() throws IOException
{
	
	
	return new NotEntity(s.getall());
	
	
}

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class NotEntity{
	List<SMNotificationDto> nots;
}