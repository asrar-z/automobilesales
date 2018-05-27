package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.SaleDto;
import com.car.entity.LineEntity;

public interface SaleDao {

	void insert(SaleDto sale) throws IOException;
	List<LineEntity> getSalesById(String vid)throws IOException;
	List<SaleDto> getSaleByOffice(int officeid, int start, int size)throws IOException;
	List<SaleDto> getAll( int start, int size) throws IOException;
	int getTotalCount()throws IOException;
	int getFilteredTotalCount(int officeid)throws IOException;
	int getrecords(String id, int office, String year);
}
