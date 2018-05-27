package com.car.dao.spec;

import java.io.IOException;
import java.util.List;

import com.car.dto.SMNotificationDto;
import com.car.entity.SMNotificationEntity;

public interface SMNotificationDao {

	public SMNotificationEntity get() throws IOException;
	void notify(SMNotificationDto n)throws IOException;
	public int getprevC();
	void updateprev(int count);
	List<SMNotificationDto> getall() throws IOException;
}
