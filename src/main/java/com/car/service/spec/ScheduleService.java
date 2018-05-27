package com.car.service.spec;



import java.text.ParseException;

import com.car.entity.ScheduleCreationEntity;
import com.car.entity.ScheduleFetchEntity;
import com.car.entity.ScheduleListEntity;
import com.car.exception.ServiceException;



public interface ScheduleService {
	
	
	ScheduleListEntity getBy(ScheduleFetchEntity entity, String id)throws ServiceException, ParseException;

	void insert(ScheduleCreationEntity s) throws ServiceException, ParseException;

	ScheduleListEntity getAll(ScheduleFetchEntity entity) throws ServiceException, ParseException;

	
}
