package com.car.service.spec;

import com.car.entity.MakeEntity;

import com.car.exception.ServiceException;

public interface MakeService {
	String getBy(int id) throws ServiceException;



	void insert(String name) throws ServiceException;

	void deleteBy(int id) throws ServiceException;

	MakeEntity getAll() throws ServiceException;
}
