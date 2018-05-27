package com.car.service.spec;


import java.util.List;



import com.car.entity.RoleEntity;
import com.car.entity.RoleFetchEntity;
import com.car.exception.ServiceException;
public interface UserRoleService {
	List<String> getBy(String userId) throws ServiceException;
	RoleEntity getBy(RoleFetchEntity entity)
			throws ServiceException;
	void insert(String userId, List<String> roleList) throws ServiceException;
	void deleteBy(String userId) throws ServiceException;

}
