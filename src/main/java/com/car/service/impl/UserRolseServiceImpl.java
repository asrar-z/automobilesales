package com.car.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.entity.RoleEntity;
import com.car.entity.RoleFetchEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.UserRoleService;
import com.car.dto.RoleDto;
import com.car.dao.spec.RoleDao;
import com.car.dao.spec.UserRoleDao;

@Service
public class UserRolseServiceImpl implements UserRoleService {
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	RoleDao roleDao;
	
	@Override
	public List<String> getBy(String userId) throws ServiceException {
		List<RoleDto> roleList;
		try {
			roleList = userRoleDao.getBy(userId);
		} catch (IOException e) {
			throw new ServiceException("Cannot fetch user for Id: " + userId, e);
		}
		
		List<String> roles = new ArrayList<String>();
		for(RoleDto role : roleList) {
			roles.add(role.getName());
		}
		return roles;
	}

	@Override
	public void insert(String userId, List<String> roleList)
			throws ServiceException {
		List<RoleDto> roles = new ArrayList<RoleDto>();
		for(String roleStr : roleList) {
			RoleDto role = new RoleDto();
			role.setName(roleStr);
			roles.add(role);
		}
		
		try {
			userRoleDao.insert(userId, roles);
		} catch (IOException e) {
			throw new ServiceException("Cannot add roles for userId: " + userId, e);
		}
		
	}

	@Override
	public void deleteBy(String userId) throws ServiceException {
		try {
			userRoleDao.deleteBy(userId);
		} catch (IOException e) {
			throw new ServiceException("Cannot delete role for userId: " + userId, e);
		}
		
	}

	@Override
	public RoleEntity getBy(RoleFetchEntity entity) throws ServiceException {
		
		try{
			int rolesize=roleDao.getTotalCount();	
			List<RoleDto> roleData =roleDao.getAll(entity.getStart(),entity.getLength());
			return new RoleEntity(entity.getDraw(),rolesize,rolesize,roleData);
			
		}catch (IOException e) {
			throw new ServiceException("Could not get roles", e);
		}
		
	}
	

}
