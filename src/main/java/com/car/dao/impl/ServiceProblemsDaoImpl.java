package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.ServiceProblemsDao;
import com.car.dto.ServiceProblemsDto;
@Repository
public class ServiceProblemsDaoImpl implements ServiceProblemsDao {
	@Autowired
	private JdbcTemplate template;
	private final static String GET="SELECT * FROM service_problems";
	private final static String GETBYID="SELECT problem FROM service_problems where id=?";
	@Override
	public List<ServiceProblemsDto> get() throws IOException {
		// TODO Auto-generated method stub
		try
		{
			return template.query(GET, (rs,rownum)->{
				return new ServiceProblemsDto(rs.getInt("id"),
				rs.getString("problem"));
			});
		}catch(DataAccessException e)
		{
			throw new IOException(e);
		}
	}
	
	@Override
	public String getby(int id)
	{return template.queryForObject(GETBYID, (rs,rownum)->{
		return rs.getString(1);
	},id);
		
	}

}
