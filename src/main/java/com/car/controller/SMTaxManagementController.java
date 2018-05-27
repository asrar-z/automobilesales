package com.car.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.TaxDao;
import com.car.dto.EmployeeDto;
import com.car.dto.TaxDto;


@Controller
public class SMTaxManagementController {
	
	@Autowired
	TaxDao taxDao;
	
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/taxmanagement")
public String showCustomerList()
{
	return "SMtemplates/taxmanagement";
}


@PreAuthorize("hasAnyAuthority('SALESMANAGER','SALESEXECUTIVE')")
@RequestMapping(value = "taxmanagement/get")
@ResponseBody
public String gettax(@RequestParam int officeid) throws IOException {
	if(officeid!=0)
		return	taxDao.getBy(officeid);
	return "select office";
}


@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value = "taxmanagement/update",  method = RequestMethod.POST)
@ResponseBody
public void update(@RequestBody TaxDto tax) throws IOException {
	if(tax.getOfficeid()!=0)
		taxDao.update(tax);
}




}
