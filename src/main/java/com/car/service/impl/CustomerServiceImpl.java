package com.car.service.impl;

import java.io.IOException;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.entity.CustomerAccountEntity;
import com.car.entity.CustomerAccountFetchEntity;
import com.car.entity.CustomerAccountListEntity;
import com.car.entity.UserAccountCreationEntity;
import com.car.entity.UserAccountEntity;
import com.car.entity.UserAccountFetchEntity;
import com.car.entity.UserAccountListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.CustomerService;
import com.car.dao.spec.CustomerDao;
import com.car.dto.CustomersDto;
import com.google.common.collect.Lists;

@Service
public class CustomerServiceImpl implements CustomerService{

	
	@Autowired
	private CustomerDao customerDao;
	
	
	
	
	@Override
	public CustomerAccountEntity getBy(String id) throws ServiceException {
 CustomersDto customer=null;
 try
 {
	 customer=customerDao.getBy(id);
	 return new CustomerAccountEntity(customer);
 }catch (IOException e) {
		throw new ServiceException("Cannot fetch customers  ", e);
	}
	}

	@Override
	public CustomerAccountListEntity getBy(CustomerAccountFetchEntity entity)
			throws ServiceException {
		List<CustomersDto> accounts=null;
		try {
			accounts = customerDao.getBy(entity.getStart(), entity.getLength());
			List<CustomerAccountEntity> entities = Lists.newArrayList();
			int customers_size=customerDao.getTotalCount();
			for (CustomersDto account : accounts) {

				CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity(account);
				entities.add(customerAccountEntity);
			}

			return new CustomerAccountListEntity(entity.getDraw(), customers_size,	customers_size, entities);
			
		} catch (IOException e) {
			throw new ServiceException("Cannot fetch customers  ", e);
		}

	}
	

	@Override
	public void insert(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException {
		String userId = userAccountCreationEntity.getUserid();

		// Insert user account
		CustomersDto customerAccount = new CustomersDto(userAccountCreationEntity);
		
		try {
			customerDao.insert(customerAccount);
		} catch (IOException e) {
			throw new ServiceException("Cannot add user account for userId: "
					+ userId, e);
		}
		
	}

	@Override
	public void update(UserAccountCreationEntity userAccountCreationEntity)
			throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBy(String id) throws ServiceException {
		try {
			customerDao.deleteBy(id);
		} catch (IOException e) {
			throw new ServiceException("Cannot add user account for userId: "
					+ id, e);
		}
		
	}

	@Override
	public CustomerAccountListEntity filter(CustomerAccountFetchEntity entity) throws ServiceException {
		List<CustomersDto> accounts=null;
		try {
			accounts = customerDao.getBy(entity.getSearchParam().toLowerCase(),entity.getStart(), entity.getLength());
			List<CustomerAccountEntity> entities = Lists.newArrayList();
			int customers_size=customerDao.getFilteredCount(entity.getSearchParam().toLowerCase());
			for (CustomersDto account : accounts) {

				CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity(account);
				entities.add(customerAccountEntity);
			}

			return new CustomerAccountListEntity(entity.getDraw(), customers_size,	customers_size, entities);
			
		} catch (IOException e) {
			throw new ServiceException("Cannot fetch customers  ", e);
		
	}
	

}
}
