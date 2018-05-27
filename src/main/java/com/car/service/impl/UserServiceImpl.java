package com.car.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;






import com.car.dao.spec.UserDao;
import com.car.dto.UserAccountDto;
import com.car.entity.UserAccountCreationEntity;
import com.car.entity.UserAccountEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.CustomerService;
import com.car.service.spec.EmployeeService;
import com.car.service.spec.UserRoleService;
import com.car.service.spec.UserService;
import com.google.common.collect.Lists;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private EmployeeService employeeService;

	public UserAccountEntity getBy(String id) throws ServiceException {
		UserAccountDto account = null;
		try {
			account = userDao.getBy(id);
		} catch (IOException e) {
			throw new ServiceException("Cannot find user for id: " + id, e);
		}

		List<String> roles = null;
		UserAccountEntity userAccountEntity = null;
		if (account != null) {
			roles = userRoleService.getBy(id);
			userAccountEntity = new UserAccountEntity(account, roles);
		}
		return userAccountEntity;
	}

	@Override
	public UserAccountListEntity getBy(UserAccountFetchEntity entity) throws ServiceException {
		
		List<UserAccountDto> accounts = null;
		int userCountByroleId;
		try {
			accounts = userDao.getBy(entity.getRoleId(), entity.getStart(), entity.getLength());
			userCountByroleId= userDao.getTotalCount(entity.getRoleId());
		} catch (IOException e) {
			throw new ServiceException("Cannot find user for officeId: "
					+ entity.getRoleId(), e);
		}

		List<UserAccountEntity> entities = Lists.newArrayList();
		for (UserAccountDto account : accounts) {
			List<String> roles = userRoleService.getBy(account.getUserid());
			//String officeName = officeService.getBy(account.getOfficeId());
			UserAccountEntity userAccountEntity = new UserAccountEntity(account, roles);
			entities.add(userAccountEntity);
		}

		return new UserAccountListEntity(entity.getDraw(), userCountByroleId,
				userCountByroleId, entities);
	}
	
	@Override
	public UserAccountListEntity filter(UserAccountFetchEntity entity)
			throws ServiceException {
		List<UserAccountDto> accounts=null;
		int userCountfilter;
		try {
			accounts = userDao.filter(entity.getSearchParam().toLowerCase(), entity.getStart(), entity.getLength());
			userCountfilter= userDao.getFilteredCount(entity.getSearchParam().toLowerCase());
		} catch (IOException e) {
			throw new ServiceException("Error occured in filtering", e);
		}
		List<UserAccountEntity> entities = Lists.newArrayList();
		for (UserAccountDto account : accounts) {
			List<String> roles = userRoleService.getBy(account.getUserid());
			//String officeName = officeService.getBy(account.getOfficeId());
			UserAccountEntity userAccountEntity = new UserAccountEntity(account, roles);
			entities.add(userAccountEntity);
		}
		
		return new UserAccountListEntity(entity.getDraw(), userCountfilter, userCountfilter, entities);
	}
		
	@Override
	@Transactional
	public void insert(	UserAccountCreationEntity userAccountCreationEntity)throws ServiceException {
		String userId = userAccountCreationEntity.getUserid();

		// Insert user account
		UserAccountDto userAccount = new UserAccountDto(userAccountCreationEntity);
		
		try {
			userDao.insert(userAccount);
		} catch (IOException e) {
			throw new ServiceException("Cannot add user account for userId: "
					+ userAccountCreationEntity.getUserid(), e);
		}

		// Delete & Insert roles
		userRoleService.deleteBy(userId);
		userRoleService.insert(userId, userAccountCreationEntity.getRoles());
		if(userAccountCreationEntity.getRoles().contains("CUSTOMER"))
		{
			customerService.insert(userAccountCreationEntity);
		}
		else
		{
			employeeService.insert(userAccountCreationEntity);
		}

	}

	@Override
	public void update(UserAccountCreationEntity userAccountCreationEntity) throws ServiceException {
		String userId = userAccountCreationEntity.getUserid();
		
		// Insert user account
		UserAccountDto userAccount = new UserAccountDto(userAccountCreationEntity);
		
		try {
			userDao.update(userAccount);
		} catch (IOException e) {
			throw new ServiceException("Cannot edit user account for userId: " + userAccountCreationEntity.getUserid(), e);
		}
		
		// Delete & Insert roles
		userRoleService.deleteBy(userId);
		userRoleService.insert(userId, userAccountCreationEntity.getRoles());
		if(userAccountCreationEntity.getRoles().contains("CUSTOMER"))
		{
			customerService.insert(userAccountCreationEntity);
		}
	}

	@Override
	public void deleteBy(String id) throws ServiceException {
		try {
			userDao.deleteBy(id);
			userRoleService.deleteBy(id);
			customerService.deleteBy(id);
		} catch (IOException e) {
			throw new ServiceException("Cannot delete user account for userId: " + id, e);
		}
	}



}
