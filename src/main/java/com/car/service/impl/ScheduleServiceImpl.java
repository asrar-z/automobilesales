package com.car.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.ScheduleDao;
import com.car.dto.ScheduleDto;
import com.car.entity.ScheduleCreationEntity;
import com.car.entity.ScheduleEntity;
import com.car.entity.ScheduleFetchEntity;
import com.car.entity.ScheduleListEntity;
import com.car.exception.ServiceException;
import com.car.service.spec.ScheduleService;
import com.google.common.collect.Lists;


@Service
public class ScheduleServiceImpl implements ScheduleService{

	
	@Autowired
	ScheduleDao scheduleDao;
	@Override
	public void insert(ScheduleCreationEntity s) throws ServiceException, ParseException {
		ScheduleDto scheduleDto=new ScheduleDto(s);
		
		try
		{
			scheduleDao.insert(scheduleDto);
		}catch(IOException e)
		{
			throw new ServiceException("Cannot create schedule ",e);
		}
		
	}

	@Override
	public ScheduleListEntity getBy(ScheduleFetchEntity entity, String id)	throws ServiceException {
		
		
		List<ScheduleDto> schedules=null;
		int schedulecount;
		
		try
		{
			schedules=scheduleDao.getBy(id, entity.getStart(),entity.getLength());
			schedulecount=scheduleDao.getTotalCount(id);
		}
		catch (IOException e) {
			throw new ServiceException("Cannot find schedules: "+ id, e);
		}
		
		List<ScheduleEntity> entities=Lists.newArrayList();
		for(ScheduleDto s:schedules)
		{
			String status="Pending";
			if(String.valueOf(s.getCompleted()).equals("1"))
			{
				status="Completed";
			}
			ScheduleEntity entity2=new ScheduleEntity(s.getApp_id(),s.getId(), s.getName(), s.getNumber(),s.getDate() ,status,s.getAssigned(),s.getOfficeid());
			entities.add(entity2);
		}
		return new ScheduleListEntity(entity.getDraw(), schedulecount, schedulecount, entities);
	}

	@Override
	public ScheduleListEntity getAll(ScheduleFetchEntity entity) 	throws ServiceException {
		List<ScheduleDto> schedules=null;
		int schedulecount;
		
		try
		{
			schedules=scheduleDao.getAll(entity.getFilter(), entity.getStart(),entity.getLength());
			schedulecount=scheduleDao.getTotalCount(entity.getFilter());
		}
		catch (IOException e) {
			throw new ServiceException("Cannot find schedules: " , e);
		}
		List<ScheduleEntity> entities=Lists.newArrayList();
		for(ScheduleDto s:schedules)
		{
			String status="Pending";
			
			if(String.valueOf(s.getCompleted()).equals("1"))
			{
				status="Completed";
			}
			else if(String.valueOf(s.getCompleted()).equals("2"))
			{
				status="SE confirmed";
			}
			ScheduleEntity entity2=new ScheduleEntity(s.getApp_id(),s.getId(), s.getName(), s.getNumber(),s.getDate() ,status,s.getAssigned(),s.getOfficeid());
			entities.add(entity2);
		}
		return new ScheduleListEntity(entity.getDraw(), schedulecount, schedulecount, entities);
	}

}
