package com.car.dao.spec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.car.dto.VehicleDto;
import com.car.entity.LineEntity;

public interface VehicleDao {

	VehicleDto getBy(String id) throws IOException;
	
	List<VehicleDto> getAll(int make_id, int office, String year)throws IOException;
	
	void insert(int office, VehicleDto vehicleDto)throws IOException, SerialException, SQLException;
	
	void update(VehicleDto vehicleDto)throws IOException;
	
	boolean delete(String id, int office)throws IOException;

	void sold(String id, int office) throws IOException;

	void insert(VehicleDto vDto, String oldid,int office) throws SerialException, SQLException, IOException;

	String getname(String id, String year);

	List<String> getyears();

	List<LineEntity> getsales(String id, int office);

	List<VehicleDto> getAllsales(int office, String year) throws IOException;
	
	int getCategory(String id,int office)throws IOException;

	void insert_without_pic(VehicleDto vDto, String oldid, int office) throws IOException;

	int getCategory2(String name, int office) throws IOException;
}
