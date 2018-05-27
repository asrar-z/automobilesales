package com.car.dao.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.VcostDao;


@Repository
public class VcostDaoImpl implements VcostDao{
	@Autowired
	private JdbcTemplate template;
	
	
	@Override	
	public String getCost(String id, String office) throws IOException {
		String column="cost"+office;
		System.out.println("getting cost for office "+ column+"and vehicle"+id);
		final String getcost="SELECT "+column+" from vehicle_cost where id=?";
		System.out.println(getcost);
		try {
			return template.queryForObject(getcost,
					(rs, rownum) -> {
						return rs.getString(1);
					},id);
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

}
