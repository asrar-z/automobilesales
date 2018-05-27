package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.EMIDto;

public interface EMIDao {
EMIDto getBy(String c_id) throws IOException;

List<EMIDto> getemi() throws IOException;

void insert(EMIDto sale) throws IOException;

void update(String c_id, String idx) throws IOException;

void update_month(int month, int id);
}
