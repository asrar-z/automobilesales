package com.car.service.spec;


import com.car.entity.VehicleEntity;
import com.car.exception.ServiceException;

public interface VehicleService {

	
	String getBy(String id) throws ServiceException;

	VehicleEntity getAll(int make_id, int office,String year) throws ServiceException;

	//void insert(String name) throws ServiceException;

	boolean deleteBy(int id) throws ServiceException;

	VehicleEntity getAllsales(int office, String year) throws ServiceException;

}
