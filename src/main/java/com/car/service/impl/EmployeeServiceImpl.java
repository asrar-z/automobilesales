package com.car.service.impl;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.EmployeeDao;
import com.car.dto.EmployeeDto;
import com.car.entity.EmployeeEntity;
import com.car.entity.EmployeeFetchEntity;
import com.car.entity.EmployeeListEntity;
import com.car.entity.UserAccountCreationEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.EmployeeService;
import com.car.service.spec.OfficeService;
import com.google.common.collect.Lists;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	
	@Autowired
	EmployeeDao employeeDao;
	@Autowired
	OfficeService officeService;
	@Override
	public void update(EmployeeDto employeeDto) throws ServiceException {
		try
		{
			employeeDao.update(employeeDto);
		}catch (IOException e) {
			throw new ServiceException("Cannot update employee detaisl for: "+employeeDto.getId(), e);
			}
		
	}
	
	@Override
	public EmployeeListEntity getAll(EmployeeFetchEntity entity) throws ServiceException {
		List<EmployeeDto> employees=null;
		int total;
		try
		{
			 total=employeeDao.getTotalCount();
			employees=employeeDao.getAll(entity.getStart(),entity.getLength());
			

		}catch (IOException e) {
			throw new ServiceException("Could not get the employees", e);
		}
		
		List<EmployeeEntity> entities = Lists.newArrayList();
		for(EmployeeDto e: employees)
		{
			String officename="Not Assigned";
			if(e.getOfficeid()!=0)
				{officename=officeService.getBy(e.getOfficeid());}
			EmployeeEntity entity2= new EmployeeEntity(e,officename);
			entities.add(entity2);
	
		}
		return new EmployeeListEntity(entity.getDraw(), total, total, entities);
		
	}
	@Override
	public void insert(UserAccountCreationEntity entity)throws ServiceException {
		try
		{
			employeeDao.insert(entity);
		}catch (IOException e) {
			throw new ServiceException("Cannot create employee: "+entity.getUserid(), e);
			}
		
	}

	@Override
	public EmployeeListEntity getBy(EmployeeFetchEntity employeeFetchEntity)
			throws ServiceException {
		List<EmployeeDto> employees=null;
		int total;
		try
		{
			 total=employeeDao.getFilteredTotalCount(employeeFetchEntity.getOfficeid());
			employees=employeeDao.getBy(employeeFetchEntity.getOfficeid(),employeeFetchEntity.getStart(),employeeFetchEntity.getLength());
			

		}catch (IOException e) {
			throw new ServiceException("Could not get the employees", e);
		}
		
		List<EmployeeEntity> entities = Lists.newArrayList();
		for(EmployeeDto e: employees)
		{
			String officename=officeService.getBy(e.getOfficeid());
			EmployeeEntity entity2= new EmployeeEntity(e,officename);
			entities.add(entity2);
		}
		return new EmployeeListEntity(employeeFetchEntity.getDraw(), total, total, entities);
		
	}
		
	}


