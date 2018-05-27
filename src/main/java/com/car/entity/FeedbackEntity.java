package com.car.entity;


import java.util.List;




import com.car.dto.FeedbackDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackEntity {


	private List<FeedbackDto> feedbacks;
	
	
	
}
