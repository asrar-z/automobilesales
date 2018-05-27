package com.car.service.spec;

import com.car.dto.FeedbackDto;
import com.car.exception.ServiceException;

public interface FeedbackService {

	
	
	void insert(FeedbackDto feedback)throws ServiceException;
}
