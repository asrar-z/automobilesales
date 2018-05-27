package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.FeedbackDto;
import com.car.entity.FeedbackEntity;


public interface FeedbackDao {

	void insert(FeedbackDto feedbackDto)throws IOException;
	List<FeedbackDto> getAll()throws IOException;
	
}
