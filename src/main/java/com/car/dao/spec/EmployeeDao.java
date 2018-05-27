package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.EmployeeDto;
import com.car.entity.EmployeeEntity;
import com.car.entity.UserAccountCreationEntity;

public interface EmployeeDao {

	int getTotalCount()throws IOException;
	void update(EmployeeDto e) throws IOException;
	List<EmployeeDto> getAll(int start,int size) throws IOException;
	void insert(UserAccountCreationEntity entity) throws IOException;
	List <EmployeeDto>get(int officeid) throws IOException;
	int getFilteredTotalCount(int officeid)throws IOException;
	List<EmployeeDto> getBy(int officeid, int start, int length)throws IOException;
	List<EmployeeDto> getSE(int id) throws IOException;
	List<EmployeeDto> getSRE() throws IOException;
	
}
