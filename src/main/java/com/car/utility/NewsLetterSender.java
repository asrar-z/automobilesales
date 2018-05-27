package com.car.utility;

import java.io.IOException;

import java.util.List;


import javax.mail.MessagingException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.car.dao.spec.CustomerDao;


@Component
public class NewsLetterSender {

	
	@Autowired
	private JavaMailSender javaMailSender;	
	@Autowired
	private SmtpMailSender smtpMailSender;
	
	@Autowired 
	private CustomerDao customerDao;
	
	public void send(int target,String subject,String body)throws MessagingException, IOException{
	
		List<String> recipients= customerDao.getmailid(target);

	for(String to: recipients){
   
		
		smtpMailSender.send(to, subject, body);
	
	}
	
}
}
