package com.car.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dao.spec.FeedbackDao;
import com.car.dto.FeedbackDto;
import com.car.entity.FeedbackEntity;
import com.car.entity.ScheduleEntity2;
import com.car.entity.ServiceFetchEntity;
import com.car.entity.jsonObj;
import com.car.utility.Crawler;

@Controller
public class BIACustomerFeedbackController {

	
	@Autowired FeedbackDao feedbackDao;
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping("/customerfeedback")
	public String showFeedback()
	{
		return "BIAtemplates/customerfeedback";
	}
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value ="/customerfeedback/get",method = RequestMethod.POST)
	@ResponseBody
	public FeedbackEntity get() throws IOException
	{
		List<FeedbackDto> feedbacks=feedbackDao.getAll();
		return new FeedbackEntity(feedbacks);
	}
	
	@PreAuthorize("hasAuthority('ANALYST')")
	@RequestMapping(value ="/customerfeedback/liverates",method = RequestMethod.POST)
	@ResponseBody
	public List<String> getlive(@RequestBody temp t ) throws IOException
	{
		System.out.println(t);
		Crawler c=new Crawler();
		return(c.crawl(t.getOffice(),t.getMake(),t.getJ()));
	}
}
@NoArgsConstructor
@Data
class temp
{
	int office;
	int make;
	List<jsonObj> j;
}
