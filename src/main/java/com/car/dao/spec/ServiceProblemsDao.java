package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.ServiceProblemsDto;

public interface ServiceProblemsDao {

List<ServiceProblemsDto> get() throws IOException;

String getby(int id);
	
}
