package com.car.dao.impl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.MakeDao;
import com.car.dto.MakeDto;




@Repository
public class MakeDaoImpl implements MakeDao {

	
	
	private static final String FETCH_MAKE_BY_ID ="SELECT * FROM MAKE WHERE id = ?"; 
	private static final String FETCH_ALL ="SELECT * FROM MAKE "; 
	private static final String INSERT_MAKE = "INSERT INTO MAKE (name) VALUES(?)";
	
	private static final String DELETE_MAKE = "DELETE FROM MAKE WHERE id = ?";
	private static final String INSERT="INSERT INTO MAKE"
			+"(name,img2)"
			+"VALUES (? , ?)";
	
	private static final String UPDATE="UPDATE MAKE SET name=?, img2=? WHERE id=? ";
	private static final String UPDATE_without_pic="UPDATE MAKE SET name=? WHERE id=? ";
	private static final String CHECK="SELECT COUNT(*)FROM VEHICLE WHERE make_id=?";
	
	@Autowired
	private JdbcTemplate template;
	
	
	@Override
	public MakeDto getBy(int id) throws IOException {
		
		try {
			return template.queryForObject(FETCH_MAKE_BY_ID, (rs, rownum) -> {
				return new MakeDto(rs.getInt("id"), rs.getString("name"), rs.getString("img"),rs.getBytes("img2"));
			}, id);
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}


	@Override
	public List<MakeDto> getAll() throws IOException {
		// TODO Auto-generated method stub
		try {
			return template.query(FETCH_ALL, (rs, rownum) -> {
				return new MakeDto(rs.getInt("id"), rs.getString("name"), rs.getString("img"),rs.getBytes("img2"));
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void insert(String name) throws IOException {
		try{
			template.update(INSERT_MAKE, ps -> ps.setString(1, name));
		}catch (DataAccessException e) {
			throw new IOException(e);
		
	}
	}

	@Override
	public boolean deleteBy(int id) throws IOException {
		try{
			
			int check=template.queryForObject(CHECK, (rs, rowNum) -> {
						return rs.getInt(1);
			},id);
			if(check>0)
			{		return false;}
		 template.update(DELETE_MAKE,
				ps -> ps.setInt(1, id));
		 return true;
			
		
	} catch (DataAccessException e) {
		throw new IOException(e);
	}
	}


	@Override
	public void insert(String name, byte[] img2) throws IOException, SerialException, SQLException {
		try
		{
			Blob blob = new javax.sql.rowset.serial.SerialBlob(img2);
			
			template.update(INSERT,(ps)->{
				ps.setString(1,name);
				ps.setBlob(2, blob);
			});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}


	@Override
	public void update(String name, byte[] img,int id) throws SerialException, SQLException, IOException {
		try
		{
			Blob blob = new javax.sql.rowset.serial.SerialBlob(img);
			
			template.update(UPDATE,(ps)->{
				ps.setString(1,name);
				ps.setBlob(2, blob);
				ps.setInt(3, id);
			});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}


	@Override
	public void update_without_logo(String name, int id) throws IOException {
		try
		{
		
			
			template.update(UPDATE_without_pic,(ps)->{
				ps.setString(1,name);
				ps.setInt(2, id);
			});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}



}
