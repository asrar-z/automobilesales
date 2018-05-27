package com.car.dao.impl;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.VehicleDao;
import com.car.dto.VehicleDto;
import com.car.entity.LineEntity;

@Repository
public class VehicleDaoImpl implements VehicleDao {




	private static final String getname="select name from vehicle where id=? and year=?";
	
	@Autowired
	private JdbcTemplate template;
	
	
	@Override
	public VehicleDto getBy(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleDto> getAll(int make_id,int office,String year) throws IOException {
		String str="vehicle";
		if(office==2)
			str+="2";
		final String FETCH_BY_MAKE= "SELECT * FROM " +str+" WHERE make_id = ? AND year="+year;
		
		try
		{
			return template.query(FETCH_BY_MAKE, (rs, rownum) -> {
				return new VehicleDto(rs.getString("id"), rs.getString("name"),rs.getInt("make_id"),rs.getString("cc"),rs.getString("torque"),rs.getString("speed"),
						rs.getString("cost"),rs.getInt("sold"),rs.getBytes("img"),rs.getInt("quantity"),rs.getString("year"),rs.getString("bp"),rs.getInt("category"));
			}, make_id);	
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
		
		
	}

	@Override
	public void insert(int office, VehicleDto vehicleDto) throws IOException, SerialException, SQLException {
try {
	Blob blob = new javax.sql.rowset.serial.SerialBlob(vehicleDto.getImg());
			String str="vehicle";
			if(office==2)
				str+="2";
			final String INSERT_VEHICLE = "INSERT INTO "+str
					+ " (id, name, make_id, cc, torque, speed, cost, sold, img, quantity,year,bp)"
					+ " VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?,?)";
			template.update(INSERT_VEHICLE, (ps) -> {
				ps.setString(1, vehicleDto.getId());
				ps.setString(2, vehicleDto.getName());
				ps.setInt(3, vehicleDto.getMake_id());
				ps.setString(4, vehicleDto.getCc());
				ps.setString(5, vehicleDto.getTorque());
				ps.setString(6, vehicleDto.getSpeed());
				ps.setString(7, vehicleDto.getCost());
				ps.setInt(8, vehicleDto.getSold());
				ps.setBlob(9, blob);
				ps.setInt(10, vehicleDto.getQuantity());
				ps.setString(11, vehicleDto.getYear());
				ps.setString(11, vehicleDto.getBp());
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void update(VehicleDto vehicleDto) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(String id,int office) throws IOException {
		String str="vehicle";
		if(office==2)
			str+="2";
		final String DELETE="DELETE FROM "+str+" WHERE id=?";
		try {
			int check= template.update(DELETE, (ps) -> ps.setString(1, id));
			if(check>0)
				return true;
			return false;
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public void sold(String id,int office) throws IOException {
	try
	{
		String str="vehicle";
		if(office==2)
			str+="2";
		final String UPDATE="UPDATE "+str+" SET sold = sold + 1, quantity=quantity-1 WHERE id=? ";
		template.update(UPDATE, (ps)->{
			
			ps.setString(1, id);
			
			
			
		});
	}catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}

	@Override
	public void insert(VehicleDto vehicleDto, String oldid,int office) throws SerialException, SQLException, IOException {
		try {
			String str="vehicle";
			if(office==2)
				str+="2";

			final String UPDATE_VEHICLE = "UPDATE "+str+" SET"
					+ " id = ?, name=?, make_id=?, cc=?, torque=?, speed=?, cost=?, img=?, quantity=?, bp=? WHERE id=? and year=?";
			Blob blob = new javax.sql.rowset.serial.SerialBlob(vehicleDto.getImg());
					
					template.update(UPDATE_VEHICLE, (ps) -> {
						ps.setString(1, vehicleDto.getId());
						ps.setString(2, vehicleDto.getName());
						ps.setInt(3, vehicleDto.getMake_id());
						ps.setString(4, vehicleDto.getCc());
						ps.setString(5, vehicleDto.getTorque());
						ps.setString(6, vehicleDto.getSpeed());
						ps.setString(7, vehicleDto.getCost());
						ps.setBlob(8, blob);
						ps.setInt(9, vehicleDto.getQuantity());
						ps.setString(10, vehicleDto.getBp());
						ps.setString(11, oldid);
						ps.setString(12, vehicleDto.getYear());
					});
				} catch (DataAccessException e) {
					throw new IOException(e);
				}
		
	}
	
	@Override
	public String getname(String id,String year)
		{return template.queryForObject(getname, (rs,rownum)->{
		return rs.getString(1);
	},id,year);
		
	}

	@Override
	public List<String> getyears() {
		return template.query("SELECT year from vehicle group by year", (rs,rownum)->{
			return rs.getString(1);
		});
	}
	
	@Override
	public List<LineEntity> getsales(String id,int office){
		String str="vehicle";
		if(office==2)
			str+="2";
		
			
		String line="SELECT sold,year from "+str+" where id=? group by year";
		return template.query(line,(ps)->{ps.setString(1,id);}, (rs,rownum)->{
			return new LineEntity(rs.getString("year"),rs.getInt("sold"));
		});
	}
	@Override
	public List<VehicleDto> getAllsales(int office,String year) throws IOException{
		String str="vehicle";
		if(office==2)
			str+="2";
		
		final String FETCH_ALL= "SELECT * FROM " +str+" WHERE year="+year;
		
		try
		{
			return template.query(FETCH_ALL, (rs, rownum) -> {
				return new VehicleDto(rs.getString("id"), rs.getString("name"),rs.getInt("make_id"),rs.getString("cc"),rs.getString("torque"),rs.getString("speed"),
						rs.getString("cost"),rs.getInt("sold"),rs.getBytes("img"),rs.getInt("quantity"),rs.getString("year"),rs.getString("bp"),rs.getInt("category"));
			});	
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getCategory(String id, int office) throws IOException {
		String str="vehicle";
		if(office==2)
			str+="2";
		Calendar now = Calendar.getInstance();
		   		int y = now.get(Calendar.YEAR);
		    		String year = String.valueOf(y);
		final String get_category="select category from "+str+" where id=? and year= "+year;
		return template.queryForObject(get_category,
				(rs,rownum)->{
					return rs.getInt("category");},id
				);
	}
	@Override
	public int getCategory2(String name, int office) throws IOException {
		String str="vehicle";
		if(office==2)
			str+="2";
		Calendar now = Calendar.getInstance();
		   		int y = now.get(Calendar.YEAR);
		    		String year = String.valueOf(y);
		final String get_category="select category from "+str+" where name=? and year= "+year;
		return template.queryForObject(get_category,
				(rs,rownum)->{
					return rs.getInt("category");},name
				);
	}
	@Override
	public void insert_without_pic(VehicleDto vehicleDto, String oldid, int office) throws IOException {
		try {
			String str="vehicle";
			if(office==2)
				str+="2";

			final String UPDATE_VEHICLE = "UPDATE "+str+" SET"
					+ " id = ?, name=?, make_id=?, cc=?, torque=?, speed=?, cost=?, bp=?, quantity=? WHERE id=? and year=?";

					
					template.update(UPDATE_VEHICLE, (ps) -> {
						ps.setString(1, vehicleDto.getId());
						ps.setString(2, vehicleDto.getName());
						ps.setInt(3, vehicleDto.getMake_id());
						ps.setString(4, vehicleDto.getCc());
						ps.setString(5, vehicleDto.getTorque());
						ps.setString(6, vehicleDto.getSpeed());
						ps.setString(7, vehicleDto.getCost());
						ps.setString(8, vehicleDto.getBp());
						ps.setInt(9, vehicleDto.getQuantity());
						ps.setString(10, oldid);
						ps.setString(11, vehicleDto.getYear());
					});
				} catch (DataAccessException e) {
					throw new IOException(e);
				}
		
	}

}
