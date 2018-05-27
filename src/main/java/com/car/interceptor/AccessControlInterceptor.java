package com.car.interceptor;


import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.car.service.spec.UserService;

@Component
public class AccessControlInterceptor extends HandlerInterceptorAdapter {



	@Autowired
	private UserService userService;

	private static final Logger logger = Logger.getLogger(AccessControlInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (request.getUserPrincipal() != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (request.getSession().getAttribute("roles") == null) {
				request.getSession().setAttribute("roles", auth.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

			}

			if (request.getRequestURI().toString().endsWith("login")) {

				response.sendRedirect(request.getContextPath() + "/logout");
				return false;
			}


		}

		
		return true;
	}
}