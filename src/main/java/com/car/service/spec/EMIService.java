package com.car.service.spec;

import com.car.dto.EMIDto;
import com.car.exception.ServiceException;

public interface EMIService {

	
	EMIDto getBy(String c_id) throws ServiceException;
	
}
