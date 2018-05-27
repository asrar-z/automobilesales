package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.RoleDao;
import com.car.dto.RoleDto;


@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	private JdbcTemplate template;
	
	private static final String FETCH_ROLES = "SELECT * FROM ROLES LIMIT ? OFFSET ?";
	
	
	@Override
	public int getTotalCount() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<RoleDto> getAll(int start, int size) throws IOException {
		try {
			return template.query(FETCH_ROLES, ps -> {
				ps.setInt(1, size);
				ps.setInt(2, start);
			}, (rs, rownum) -> {
				return new RoleDto(rs.getString("id"),rs.getString("role"));
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public void insert(String name) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteBy(int id) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
