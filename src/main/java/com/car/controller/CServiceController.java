package com.car.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.ServiceProblemsDao;
import com.car.dto.ServiceProblemsDto;
import com.car.entity.ServiceCreationEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ServiceService;


@Controller
public class CServiceController {
	
	@Autowired
	ServiceService serviceService;
	@Autowired
	ServiceProblemsDao p;
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/service")
	public String showService()
	{
		return "CStemplates/service";
	}
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/trackservice")
	public String trackService()
	{
		return "CStemplates/trackservice";
	}
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="models/problems")
	@ResponseBody
	public List<ServiceProblemsDto> getproblems() throws IOException
	{
		return p.get();
	}
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="service/add", method = RequestMethod.POST)
	@ResponseBody
	public void shedule (@RequestBody ServiceCreationEntity s) throws ServiceException, ParseException
	{
		System.out.println(s.getName()+s.getNumber()+s.getId()+s.getDate());
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	      String name = auth.getName(); 
		s.setId(name);
		serviceService.insert(s);

	}

}
