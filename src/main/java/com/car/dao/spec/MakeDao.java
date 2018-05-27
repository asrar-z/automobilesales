package com.car.dao.spec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.car.dto.MakeDto;

public interface MakeDao {

	MakeDto getBy(int id) throws IOException;


	List<MakeDto> getAll() throws IOException;

	void insert(String name) throws IOException;

	boolean deleteBy(int id) throws IOException;
	
	void insert(String name, byte[] img2) throws IOException, SerialException, SQLException;

	void update(String name, byte[] bytes, int id) throws SerialException, SQLException, IOException;


	void update_without_logo(String name, int id) throws IOException;
}
