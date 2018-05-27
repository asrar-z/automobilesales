package com.car.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.MakeDao;
import com.car.dto.Sdata;

@Repository
public class ChennaiDao {
	@Autowired
	private JdbcTemplate template;

	
	private static final String a="SELECT * FROM chennaidata";
	
	public List<Sdata> get()
	{
		return template.query(a, (rs,rownum)->{
			return new Sdata(rs.getInt("Position"),
			rs.getString("Brand"),
			rs.getString("Units"));
		});
	}
	
}

