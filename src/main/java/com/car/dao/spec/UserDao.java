package com.car.dao.spec;
import java.io.IOException;
import java.util.List;

import com.car.dto.UserAccountDto;
public interface UserDao {
	UserAccountDto getBy(String id) throws IOException;

	List<UserAccountDto> getBy(int roleId, int start, int size)
			throws IOException;
	List<UserAccountDto> filter(String searchParam, int start, int size) throws IOException;
	int getTotalCount(int roleId) throws IOException;
	int getFilteredCount(String searchParam) throws IOException;
	void insert(UserAccountDto userAccount) throws IOException;

	void update(UserAccountDto userAccount) throws IOException;

	void deleteBy(String id) throws IOException;

}
