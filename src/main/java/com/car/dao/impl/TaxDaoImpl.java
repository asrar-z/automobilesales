package com.car.dao.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.TaxDao;
import com.car.dto.TaxDto;


@Repository
public class TaxDaoImpl implements TaxDao {

	
	@Autowired
	private JdbcTemplate template;
	
	
	private static final String FETCH_BY_ID="SELECT rate FROM TAX WHERE officeid=?";
	private static final String UPDATE="UPDATE TAX SET rate=? WHERE officeid=?";
	private static final String INSERT="INSERT INTO TAX(officeid,rate) values (?,7)";
	@Override
	public String getBy(int officeid) throws IOException {

		try {
			return template.queryForObject(FETCH_BY_ID,
					(rs, rownum) -> {
						return String.valueOf(rs.getInt("rate"));
					}, officeid);
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
		
	}
	@Override
	public void update(TaxDto tax) throws IOException {
		try {
		template.update(UPDATE, (ps) -> {
			
			ps.setInt(1, tax.getRate());
			ps.setInt(2, tax.getOfficeid());
			
			
		});
	}
	catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}
	
	@Override
	public void insert(TaxDto tax)throws IOException{
		template.update(INSERT,(ps)->{
			ps.setInt(1,tax.getOfficeid());
		});
	}

}
