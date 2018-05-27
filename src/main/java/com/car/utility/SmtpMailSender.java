package com.car.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpMailSender {
	
	@Autowired
	private JavaMailSender javaMailSender;	
	
	public void send(String to, String subject, String body) throws MessagingException {
		System.out.println("smtp says: sending");
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		
		helper = new MimeMessageHelper(message, true); // true indicates
		helper.setFrom("zautosolutions@gmail.com");											   // multipart message
		helper.setSubject(subject);
		helper.setTo(to);
		helper.setText(body, true); // true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  

		javaMailSender.send(message);
		System.out.println("smtp says: sent");
		
		
	}

}
