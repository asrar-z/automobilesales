package com.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dto.EmployeeDto;
import com.car.entity.EmployeeEntity;
import com.car.entity.EmployeeFetchEntity;
import com.car.entity.EmployeeListEntity;
import com.car.service.spec.EmployeeService;


@Controller
public class SMEmployeeManagementController {
	
	@Autowired
	EmployeeService employeeService;
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/employees")
public String showCustomerList()
{
	return "SMtemplates/employees";
}


@PreAuthorize("hasAnyAuthority('SALESMANAGER')")
@RequestMapping(value = "employees/get", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public EmployeeListEntity get(@RequestBody EmployeeFetchEntity employeeFetchEntity)
{
	if(employeeFetchEntity.getOfficeid()==0)
	return employeeService.getAll(employeeFetchEntity);
	else
	return employeeService.getBy(employeeFetchEntity);
}



@PreAuthorize("hasAnyAuthority('SALESMANAGER')")
@RequestMapping(value = "employees/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public void update(@RequestBody EmployeeDto employee)
{
	System.out.println(employee);
	 employeeService.update(employee);
}




}