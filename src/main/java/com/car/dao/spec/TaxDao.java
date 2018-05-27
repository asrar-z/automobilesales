package com.car.dao.spec;

import java.io.IOException;

import com.car.dto.TaxDto;

public interface TaxDao {

	String getBy(int officeid) throws IOException;

	void update(TaxDto tax) throws IOException;

	void insert(TaxDto tax) throws IOException;
	
}
