package com.car.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.EMIDao;
import com.car.dto.EMIDto;
import com.car.entity.CustomerAccountListEntity;
import com.car.entity.EMIEntity;
import com.car.service.spec.CustomerService;
import com.car.service.spec.EMIService;


@Controller
public class SMEmiManagementController {
	
	
	
	@Autowired
	EMIService emiService;
	@Autowired
	EMIDao emiDao;
	
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/emi")
public String showCustomerList()
{
	return "SMtemplates/emi-management";
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/emi/get")
@ResponseBody
public EMIEntity showemi() throws IOException
{
	
	
	EMIEntity e=new EMIEntity(emiDao.getemi());
	System.out.println(e);
	return e;
	
}


}
