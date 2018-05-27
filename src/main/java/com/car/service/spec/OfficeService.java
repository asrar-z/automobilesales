package com.car.service.spec;

import com.car.entity.OfficeEntity;
import com.car.entity.OfficeFetchEntity;
import com.car.exception.ServiceException;

public interface OfficeService {
	String getBy(int officeId) throws ServiceException;

	OfficeEntity getBy(OfficeFetchEntity entity) throws ServiceException;

	void insert(String name) throws ServiceException;

	boolean deleteBy(int id) throws ServiceException;
}
