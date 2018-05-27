package com.car.dao.spec;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import com.car.dto.ScheduleDto;


public interface ScheduleDao {

	ScheduleDto getBy(String id)throws IOException;
	
	int getTotalCount(String id) throws IOException;
	int getTotalCount(int filter) throws IOException;
	List<ScheduleDto> getBy(String id,int start, int size) throws IOException;
	
	List<ScheduleDto> getAll(int filter,int start, int size)throws IOException;
	
	void insert(ScheduleDto userSchedule) throws IOException, ParseException;
	
	void update(Timestamp date, String id) throws IOException;
	
	List<ScheduleDto> gettoday()throws IOException;

	void updateSE(int app_id, String se_id) throws IOException;

	void update(int id,Byte b);

	int getSECount(String id) throws IOException;
	
}
