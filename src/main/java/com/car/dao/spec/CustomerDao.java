package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.CustomersDto;

public interface CustomerDao {
	CustomersDto getBy(String id) throws IOException;

	List<CustomersDto> getBy( int start, int size)
			throws IOException;

	void insert(CustomersDto customerAccount) throws IOException;

	void update(CustomersDto customerAccount) throws IOException;

	void deleteBy(String id) throws IOException;
	
	int getTotalCount() throws IOException;

	List<CustomersDto> getBy(String searchParam, int start, int length)throws IOException;

	int getFilteredCount(String lowerCase)throws IOException;


	List<String> getId();

	List<String> getmailid(int category) throws IOException;
}

