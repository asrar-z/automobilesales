package com.car.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	
	private static final Logger logger = Logger.getLogger(LoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		logger.debug("Authorities : " +authorities);
		 boolean isCustomer = false;
	        boolean isSalesManager = false;
	        boolean isSalesExecutive = false;
	        boolean isSalesSupport = false;
	        boolean isBIA = false;
	        boolean isWHManager = false;
	        boolean isAdmin = false;
	        boolean isServiceExecutive=false;
	        for (GrantedAuthority grantedAuthority : authorities) {
	            if (grantedAuthority.getAuthority().equals("CUSTOMER")) {
	            	isCustomer = true;
	            	logger.debug("customer has logged in");
	                break;
	            } else if (grantedAuthority.getAuthority().equals("SALESMANAGER")) {
	                isSalesManager = true;
	                logger.debug("Sales Manager has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("SALESEXECUTIVE")) {
	                isSalesExecutive = true;
	                logger.debug("Sales Executive has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("SERVICEEXECUTIVE")) {
	                isServiceExecutive = true;
	                logger.debug("Sales Manager has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("SUPPORT")) {
	                isSalesSupport = true;
	                logger.debug("Sales Support has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("ANALYST")) {
	                isBIA = true;
	                logger.debug("BIA has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("WAREHOUSEMANAGER")) {
	                isWHManager = true;
	                logger.debug("WHM has logged in");
	                break;
	            }else if (grantedAuthority.getAuthority().equals("ADMIN")) {
	                isAdmin = true;
	                logger.debug("Admin has logged in");
	                break;
	            }
	        }
	        if (isCustomer) {
	        	logger.debug("redirecting to models.html");
				response.sendRedirect(request.getContextPath() + "/models");
				
	        } else if (isSalesManager) {
	        	logger.debug("redirecting to employees.html");
				response.sendRedirect(request.getContextPath() + "/employees" );
				
	        } else if (isSalesExecutive) {
	        	logger.debug("Redirecting to appointmentsoftheday.html");
				response.sendRedirect(request.getContextPath() + "/appointmentsoftheday" );
				
	        } else if (isServiceExecutive) {
	        	logger.debug("Redirecting to serviceappointmentsoftheday.html");
				response.sendRedirect(request.getContextPath() + "/servicesoftheday" );
				
	        } else if (isSalesSupport) {
	        	 logger.debug("Redirecting to support.html");
				response.sendRedirect(request.getContextPath() + "/support" );
				
	        } else if (isBIA) {
	        	   logger.debug("Redirecting to insight.html");
				response.sendRedirect(request.getContextPath() + "/insight");
				
	        } else if (isWHManager) {
	        	logger.debug("Redirecting to  models.html");
				response.sendRedirect(request.getContextPath() + "/inventory");
				
	        } else if (isAdmin) {
	        	logger.debug("Redirecting to admin.html");
				response.sendRedirect(request.getContextPath() + "/admin");
			
	        } 
	        else {
	            throw new IllegalStateException();
	        }
	    }
		
	}
	


