package com.car.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.car.entity.CurrentUser;
import com.car.entity.UserAccountEntity;
import com.car.service.spec.CurrentUserDetailsService;
import com.car.service.spec.UserService;

@Service
public class CurrentUserDetailsServiceImpl implements CurrentUserDetailsService {

    private final UserService userService;

    @Autowired
    public CurrentUserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUser loadUserByUsername(String id) throws UsernameNotFoundException {
        UserAccountEntity user = userService.getBy(id);
        if (user == null) {
        	throw new UsernameNotFoundException(String.format("User with id=%s was not found", id));
        }
        
        return new CurrentUser(user);
    }

}
