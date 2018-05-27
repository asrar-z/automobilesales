package com.car.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	private static final String APP_NAME = "CAR Store Application";
	@RequestMapping(value = {"/", "/index"})
	public String index(HttpServletRequest request, HttpServletResponse response,Model model, HttpSession session) throws IOException {
		
		 session=request.getSession();
		if(session!=null)
		{
			session.invalidate();
			
		}
		model.addAttribute("appName", APP_NAME);
	
		
		return "index";
	}
}