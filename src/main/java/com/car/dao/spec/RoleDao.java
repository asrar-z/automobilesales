package com.car.dao.spec;


import java.io.IOException;
import java.util.List;

import com.car.dto.RoleDto;
public interface RoleDao {


	int getTotalCount() throws IOException;
	List<RoleDto> getAll(int start, int size) throws IOException;
	void insert(String name) throws IOException;
	boolean deleteBy(int id) throws IOException;
	
}
