package com.car.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.mail.MessagingException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




import com.car.dao.impl.EMIDaoImpl;
import com.car.dao.spec.SMNotificationDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.EMIDto;
import com.car.dto.SMNotificationDto;
import com.car.entity.MailEntity;
import com.car.utility.NewsLetterSender;
import com.car.utility.SmtpMailSender;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;



@Controller
public class SMNewsLettersController {

@Autowired
private NewsLetterSender newsLetterSender;
@Autowired
private EMIDaoImpl e;
@Autowired
private SmtpMailSender mail;
@Autowired SMNotificationDao n;
@Autowired VehicleDao v;
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/newsletter")
public String shownewsletterview()
{
	return "SMtemplates/newsletter";
}

@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/fbpost")
public String post()
{
	return "SMtemplates/posttofb";
}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/newsletter/send")
@ResponseBody
public void send(@RequestBody MailEntity entity ) throws Exception
{

newsLetterSender.send(entity.getTarget(),entity.getSubject(),entity.getMessage());

}
@PreAuthorize("hasAuthority('SALESMANAGER')")
@RequestMapping(value="/newsletter/fb")
@ResponseBody
public void post(@RequestBody Post p ) throws Exception
{

	Facebook facebook = new FacebookFactory().getInstance();
	facebook.postLink(new URL(p.getLink()), p.getContent());

}

@PreAuthorize("hasAuthority('ANALYST')")
@RequestMapping(value="/newsletter/newsnot",method=RequestMethod.POST)
@ResponseBody
public void newsnot(@RequestParam String vehicle,@RequestParam int office) throws Exception
{
int cat=v.getCategory2(vehicle, office);
n.notify(new SMNotificationDto(vehicle,"Send Newsletters", "The sales of "+vehicle+"- category("+ cat+ ") are decreasing. Send discounts and post on social media"));


}


@Scheduled(cron="00 30 10 1 * *")
public void mailtask() throws IOException, MessagingException
{
	List<EMIDto> customers=e.getemi();
	for(EMIDto emi:customers)
	{
		int idx=emi.getStatus().indexOf("pending");
		System.out.println("index of pending :" + idx);
		e.update_month(idx,emi.getId());
		if(emi.getStatus().contains("pending")){
			System.out.println("Reminding EMI customers");
			mail.send(emi.getEmail(),"Payment Reminder","You have EMI paments to be settled this month. Please ignore this mail if already paid");
		}
	}
}


@Scheduled(cron="00 30 10 2 * *")
public void notificationtask() throws IOException, MessagingException
{
	List<EMIDto> customers=e.getemi();
	for(EMIDto emi:customers)
	{
		int idx=emi.getMonth();
		System.out.println("Pending for "+emi.getC_id()+"month : "+(idx+1) +"status :"+emi.getStatus().get(idx));
		if(emi.getStatus().get(idx).equalsIgnoreCase("pending"))
		{
			n.notify(new SMNotificationDto(emi.getC_id(),"Pending payment",emi.getC_id()+"  failed to pay their installment for month " + (idx+1)));
		}
		
	}
}



}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Post{
	String  link;
	String content;
}
