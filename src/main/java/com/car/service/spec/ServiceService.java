package com.car.service.spec;

import java.io.IOException;
import java.text.ParseException;



import com.car.entity.ServiceCreationEntity;
import com.car.entity.ServiceFetchEntity;
import com.car.entity.ServiceListEntity;
import com.car.exception.ServiceException;

public interface ServiceService {

	ServiceListEntity getBy(ServiceFetchEntity entity, String id)throws ServiceException, IOException;
	void insert(ServiceCreationEntity serviceCreationEntity)throws ServiceException, ParseException;
	ServiceListEntity getAll(ServiceFetchEntity entity) throws IOException;
}
