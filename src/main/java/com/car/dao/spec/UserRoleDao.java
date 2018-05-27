package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.RoleDto;

public interface UserRoleDao {
	List<RoleDto> getBy(String userId) throws IOException;
	void insert(String userId, List<RoleDto> roleList) throws IOException;
	void deleteBy(String userId) throws IOException; 
}
