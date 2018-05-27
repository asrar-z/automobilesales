package com.car.service.spec;

import com.car.entity.CustomerAccountEntity;
import com.car.entity.CustomerAccountFetchEntity;
import com.car.entity.CustomerAccountListEntity;
import com.car.entity.UserAccountCreationEntity;
import com.car.entity.UserAccountEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.exception.ServiceException;

public interface CustomerService {
	
	CustomerAccountEntity getBy(String id) throws ServiceException;

	CustomerAccountListEntity getBy(CustomerAccountFetchEntity entity)
			throws ServiceException;

	void insert(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException;

	void update(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException;

	void deleteBy(String id) throws ServiceException;

	CustomerAccountListEntity filter(CustomerAccountFetchEntity entity);
}
