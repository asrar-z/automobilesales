package com.car.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.VehicleDao;
import com.car.dto.VehicleDto;
import com.car.entity.VehicleEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.VehicleService;

@Service
public class VehicleServiceImpl implements VehicleService {

	
	@Autowired
	private VehicleDao vehicleDao;
	
	@Override
	public String getBy(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VehicleEntity getAll(int make_id,int office, String year) throws ServiceException {

		List<VehicleDto> vehicles=null;
		try
		{
			vehicles=vehicleDao.getAll(make_id,office,year);
		}
		catch(IOException e)
		{
			throw new ServiceException("Couldn't get Vehicles",e);
		}
		return new VehicleEntity(vehicles);
		
		
	}
	@Override
	public VehicleEntity getAllsales(int office,String year) throws ServiceException {

		List<VehicleDto> vehicles=null;
		try
		{
			vehicles=vehicleDao.getAllsales(office,year);
		}
		catch(IOException e)
		{
			throw new ServiceException("Couldn't get Vehicles",e);
		}
		return new VehicleEntity(vehicles);
		
		
	}
	@Override
	public boolean deleteBy(int id) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
