package com.car.dao.spec;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;






import com.car.dto.ServiceDto;


public interface ServiceDao {
public void insert(ServiceDto serviceDto)throws IOException, ParseException;
int getTotalCount(String id) throws IOException;
List<ServiceDto> getBy(String id,int start, int size) throws IOException;
public List<ServiceDto> getAll(int filter, int start, int length)  throws IOException;
public int getTotalCount(int filter)  throws IOException;
public void update(Timestamp t, String id) throws IOException;
List<ServiceDto> gettoday();
void updatetrack(int app_id, String track) throws IOException;
void cancel(int app_id);
public void updateSRE(int app_id, String sre_id) throws IOException;
void update(int id);
public int getSRECount(String id) throws IOException;
}
