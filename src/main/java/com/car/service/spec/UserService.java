package com.car.service.spec;

import com.car.entity.UserAccountCreationEntity;
import com.car.entity.UserAccountEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.exception.ServiceException;

public interface UserService {

	
	UserAccountEntity getBy(String id) throws ServiceException;

	UserAccountListEntity getBy(UserAccountFetchEntity entity)
			throws ServiceException;

	UserAccountListEntity filter(UserAccountFetchEntity entity)
			throws ServiceException;
	
	void insert(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException;

	void update(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException;

	void deleteBy(String id) throws ServiceException;

	
}
