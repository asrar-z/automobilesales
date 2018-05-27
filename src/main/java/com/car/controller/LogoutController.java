package com.car.controller;

import java.io.IOException;




import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.car.dao.spec.SMNotificationDao;


@Controller
public class LogoutController {
@Autowired SMNotificationDao smnot;
@RequestMapping(value="/logout")
public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

	HttpSession session=request.getSession();
	if(session!=null)
	{
		session.invalidate();
	}
	response.sendRedirect("");
}
}
