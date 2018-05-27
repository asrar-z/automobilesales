package com.car.service.spec;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.car.entity.CurrentUser;
import com.car.exception.ServiceException;


public interface CurrentUserDetailsService extends UserDetailsService {
	
	CurrentUser loadUserByUsername(String id) throws ServiceException;
}
