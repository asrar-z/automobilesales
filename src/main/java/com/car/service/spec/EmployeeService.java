package com.car.service.spec;

import com.car.dto.EmployeeDto;
import com.car.entity.EmployeeFetchEntity;
import com.car.entity.EmployeeListEntity;
import com.car.entity.UserAccountCreationEntity;
import com.car.exception.ServiceException;

public interface EmployeeService {

	
	void update(EmployeeDto employeeDto) throws ServiceException;
	
	EmployeeListEntity getAll(EmployeeFetchEntity employeeFetchEntity) throws ServiceException;

	void insert(UserAccountCreationEntity userAccountCreationEntity)throws ServiceException;

	EmployeeListEntity getBy(EmployeeFetchEntity employeeFetchEntity)throws ServiceException;
	
	
}
