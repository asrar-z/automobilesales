package com.car.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.dao.spec.FeedbackDao;
import com.car.dto.FeedbackDto;
import com.car.exception.ServiceException;
import com.car.service.spec.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackDao feedbackdao;
	
	
	@Override
	public void insert(FeedbackDto feedback) throws ServiceException {
	
		try
		{
			feedbackdao.insert(feedback);
		} catch (IOException e) {
			throw new ServiceException("Cannot insert feedback for user: "
					+feedback.getFirstname(), e);
		
		
		}

}
}