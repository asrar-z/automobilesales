package com.car.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.EMIDao;
import com.car.dto.EMIDto;
import com.car.exception.ServiceException;
import com.car.service.spec.EMIService;


@Service
public class EMIServiceImpl implements EMIService{

	
	@Autowired
	EMIDao emiDao;
	
	@Override
	public EMIDto getBy(String c_id) throws ServiceException {
		
		EMIDto emiDto=new EMIDto();
		try
		{
			emiDto=emiDao.getBy(c_id);
		}
		catch (IOException e) {
			throw new ServiceException("Cannot find emi for c Id: "
					+ c_id, e);
			}
		
		
		return emiDto;
	}

}
