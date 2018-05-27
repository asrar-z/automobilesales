package com.car.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.MakeDao;
import com.car.dao.spec.ServiceDao;
import com.car.dao.spec.ServiceProblemsDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.MakeDto;
import com.car.dto.ServiceDto;
import com.car.entity.ServiceEntity;
import com.car.entity.ServiceListEntity;
import com.car.entity.ServiceCreationEntity;
import com.car.entity.ServiceFetchEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ServiceService;
import com.google.common.collect.Lists;


@Service
public class ServiceServiceImpl implements ServiceService{

	@Autowired
	ServiceDao serviceDao;
	
	
	@Autowired ServiceProblemsDao sp;
	@Autowired MakeDao m;
	@Autowired VehicleDao v;
	@Override
	public void insert(ServiceCreationEntity serviceCreationEntity)
			throws ServiceException, ParseException {
	ServiceDto serviceDto=new ServiceDto(serviceCreationEntity);
	try
	{
		serviceDao.insert(serviceDto);
	}catch(IOException e)
	{
		throw new ServiceException("Cannot create Service ",e);
	}
		
	}
	@Override
	public ServiceListEntity getBy(ServiceFetchEntity entity, String id)
			throws ServiceException, IOException {

		
		List<ServiceDto> Services=null;
		int Servicecount;
		
		try
		{
			Services=serviceDao.getBy(id, entity.getStart(),entity.getLength());
			Servicecount=serviceDao.getTotalCount(id);
		}
		catch (IOException e) {
			throw new ServiceException("Cannot find Services: "+ id, e);
		}
		
		List<ServiceEntity> entities=Lists.newArrayList();
		for(ServiceDto s:Services)
		{
			String status="Pending";
			if(String.valueOf(s.getCompleted()).equals("1"))
			{
				status="Completed";
			}
			else if(String.valueOf(s.getCompleted()).equals("2"))
			{
				status="Cancelled";
			}
			String problem=sp.getby(s.getProblem());
			System.out.println(problem);
			MakeDto make=m.getBy(s.getMake());
			String m=make.getName();
					Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
			String model=v.getname(s.getModel(),year);
			ServiceEntity entity2=new ServiceEntity(s.getApp_id(),s.getId(), s.getName(), s.getNumber(),s.getDate(),s.getAddress() ,status,s.getAssigned(),m,model,problem,s.getTrack());
			entities.add(entity2);
		}
		return new ServiceListEntity(entity.getDraw(), Servicecount, Servicecount, entities);
		
		
		
	}
	@Override
	public ServiceListEntity getAll(ServiceFetchEntity entity) throws IOException {
		List<ServiceDto> services=null;
		int servicecount;
		
		try
		{
			services=serviceDao.getAll(entity.getFilter(), entity.getStart(),entity.getLength());
			servicecount=serviceDao.getTotalCount(entity.getFilter());
		}
		catch (IOException e) {
			throw new ServiceException("Cannot find services: " , e);
		}
		List<ServiceEntity> entities=Lists.newArrayList();
		for(ServiceDto s:services)
		{
			String status="Pending";
			
			if(String.valueOf(s.getCompleted()).equals("1"))
			{
				status="Completed";
			}
			else if(String.valueOf(s.getCompleted()).equals("2"))
			{
				status="Cancelled";
			}
			String problem=sp.getby(s.getProblem());
			MakeDto make=m.getBy(s.getMake());
			String m=make.getName();
					Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		String year = String.valueOf(y);
			String model=v.getname(s.getModel(),year);
			ServiceEntity entity2=new ServiceEntity(s.getApp_id(),s.getId(), s.getName(), s.getNumber(),s.getDate(),s.getAddress() ,status,s.getAssigned(),m,model,problem,s.getTrack());
			entities.add(entity2);
		}
		System.out.println(entities);
		return new ServiceListEntity(entity.getDraw(), servicecount, servicecount, entities);
	}
	}




