package com.car.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.EMIDao;
import com.car.dto.EMIDto;
import com.car.service.spec.EMIService;


@Controller
public class CTrackemiController {
	
	
	
	@Autowired
	EMIService emiService;
	@Autowired EMIDao emiDao;
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/trackemi")
	public String showTrackemi()
	{
		return "CStemplates/trackemi";
	}

	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="trackemi/get",method = RequestMethod.POST)
	@ResponseBody
	public EMIDto get()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String c_id=auth.getName();
		return emiService.getBy(c_id);
	}
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="trackemi/update")
	@ResponseBody
	public void get(@RequestParam String idx) throws IOException
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String c_id=auth.getName();
		 emiDao.update(c_id,idx);
	}
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/pay")
	public String payment()
	{
		return "CStemplates/gateway";
	}
}
