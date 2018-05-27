package com.car.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.car.dto.FeedbackDto;
import com.car.service.spec.FeedbackService;


@Controller
public class CFeedbackController{
	
	@Autowired
	FeedbackService feedbackService;
	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value="/feedback")
	public String showFeedbackForm()
	{
		return "CStemplates/feedback";
	}

	
	@PreAuthorize("hasAuthority('CUSTOMER')")
	@RequestMapping(value = "feedback/insert", method = RequestMethod.POST)
	@ResponseBody
	public void insert(@RequestBody FeedbackDto feedback)
	{
		feedbackService.insert(feedback);
	}
}
