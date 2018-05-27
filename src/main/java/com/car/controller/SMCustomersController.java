package com.car.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.SMNotificationDao;
import com.car.entity.CustomerAccountFetchEntity;
import com.car.entity.CustomerAccountListEntity;
import com.car.entity.MailEntity;
import com.car.entity.SMNotificationEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.service.spec.CustomerService;
import com.car.utility.SmtpMailSender;
import com.google.common.base.Strings;



@Controller
public class SMCustomersController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private SmtpMailSender smtpMailSender;
	@Autowired SMNotificationDao smnotDao;
	
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers")
public String showCustomerList()
{
	return "SMtemplates/customers";
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers/list",  method = RequestMethod.POST)
@ResponseBody
public CustomerAccountListEntity displayCustomers(@RequestBody CustomerAccountFetchEntity entity)
{
	if (Strings.isNullOrEmpty(entity.getSearchParam()))
	{
	return customerService.getBy(entity);}
	else
		return customerService.filter(entity);
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers/mail",  method = RequestMethod.POST)
@ResponseBody
public void sendMail(@RequestBody MailEntity entity) throws MessagingException {
	System.out.println(entity.getEmailid()+ entity.getSubject()+ entity.getMessage());
	smtpMailSender.send(entity.getEmailid(), entity.getSubject(), entity.getMessage());
	
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers/notifications")
@ResponseBody
public SMNotificationEntity poll() throws IOException{
	return smnotDao.get();
}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers/getc")
@ResponseBody
public int prevC() throws IOException{
	return smnotDao.getprevC();
}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/customers/updatec")
@ResponseBody
public void prevC(@RequestParam int count) throws IOException{
	smnotDao.updateprev(count);
}
}
