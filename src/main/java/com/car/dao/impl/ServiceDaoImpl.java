package com.car.dao.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.SMNotificationDao;
import com.car.dao.spec.ServiceDao;
import com.car.dto.SMNotificationDto;
import com.car.dto.ScheduleDto;
import com.car.dto.ServiceDto;

@Repository
public class ServiceDaoImpl implements ServiceDao{


	@Autowired
	private JdbcTemplate template;
	@Autowired SMNotificationDao n;
	private final static String INSERT_SERVICE="INSERT INTO SERVICE_APPOINTMENTS"
			+ "(id, name, number, date, address,make,model,problem)"
			+ "values (?, ?, ?, ?, ?,?,?,?)";
	
	
	
	private static final String FETCH_BY_ID="SELECT * FROM SERVICE_APPOINTMENTS WHERE id = ? LIMIT ? OFFSET ?";
	private static final String COUNT="SELECT COUNT(*) FROM SERVICE_APPOINTMENTS WHERE id = ? ";
	private static final String FETCH="SELECT * FROM SERVICE_APPOINTMENTS LIMIT ? OFFSET ?";
	private static final String FETCH_TODAY="SELECT * FROM SERVICE_APPOINTMENTS WHERE DATE(date)=CURDATE() LIMIT ? OFFSET ?";
	private static final String FETCH_TODAY2="SELECT * FROM SERVICE_APPOINTMENTS WHERE DATE(date)=CURDATE() ";
	private static final String COUNT_ALL="SELECT COUNT(*) FROM SERVICE_APPOINTMENTS";
	private static final String COUNT_TODAY="SELECT COUNT(*) FROM SERVICE_APPOINTMENTS where DATE(date)=CURDATE()";
	private static final String UPDATE = "UPDATE SERVICE_APPOINTMENTS SET completed = ? WHERE id = ? AND date=?";
	private static final String UPDATEtrack="UPDATE SERVICE_APPOINTMENTS SET track=? WHERE app_id=?";
	private static final String CANCEL="UPDATE SERVICE_APPOINTMENTS SET completed=? where app_id=?";
	private static final String UPDATESRE="UPDATE SERVICE_APPOINTMENTS SET assigned=? where app_id=?";
	private static final String UPDATE2="update service_appointments set completed=1 where app_id=?";
	private static final String GETSRE="SELECT COUNT(*) FROM service_appointments where assigned=? AND DATE(date)=CURDATE() ";

	@Override
	public void insert(ServiceDto serviceDto) throws IOException, ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    java.util.Date parsedDate = dateFormat.parse(serviceDto.getDate());
	    Timestamp  timestamp = new java.sql.Timestamp(parsedDate.getTime());
	    try{
			template.update(INSERT_SERVICE, (ps) -> {
				ps.setString(1, serviceDto.getId());
				ps.setString(2, serviceDto.getName());
				ps.setString(3, serviceDto.getNumber());
				ps.setTimestamp(4, timestamp);
				ps.setString(5, serviceDto.getAddress());
				ps.setInt(6, serviceDto.getMake());
				ps.setString(7, serviceDto.getModel());
				ps.setInt(8, serviceDto.getProblem());
			});
			n.notify(new SMNotificationDto(serviceDto.getId(), "New Service Booking",serviceDto.getId()+ "-Booked a new door pick up service" ));
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public int getTotalCount(String id) throws IOException {
		try
		{
			return template.queryForObject(COUNT,
					(rs, rownum) -> {
						return rs.getInt(1);
					}, id);
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<ServiceDto> getBy(String id, int start, int size) throws IOException {
	try
	{
		return template.query(
				FETCH_BY_ID,
				ps -> {
					ps.setString(1, id);
					ps.setInt(2, size);
					ps.setInt(3, start);
				},
				(rs, rownum) -> {
					return new ServiceDto(rs.getInt("app_id"),rs.getString("id"), rs
							.getString("name"), 
							rs.getString("number"), 
							rs.getString("date"), 
							rs.getString("address"), 
							rs.getByte("completed"),
							rs.getString("assigned"),
							rs.getInt("make"),
							rs.getString("model"),
							rs.getInt("problem"),
							rs.getString("track"));
				});
	}catch (DataAccessException e) {
		throw new IOException(e);
	}
	}

	@Override
	public List<ServiceDto> getAll(int filter, int start, int size)
			throws IOException {
		try
		{
			if(filter==0)
			{return template.query(
					FETCH,
					ps -> {
						ps.setInt(1, size);
						ps.setInt(2, start);
					},
					(rs, rownum) -> {
						return new ServiceDto(rs.getInt("app_id"),rs.getString("id"), rs
								.getString("name"), 
								rs.getString("number"), 
								rs.getString("date"), 
								rs.getString("address"), 
								rs.getByte("completed"),
								rs.getString("assigned"),
								rs.getInt("make"),
								rs.getString("model"),
								rs.getInt("problem"),rs.getString("track")								);
					});}
			else
			{
				return template.query(
						FETCH_TODAY,
						ps -> {
							ps.setInt(1, size);
							ps.setInt(2, start);
						},
						(rs, rownum) -> {
							return new ServiceDto(rs.getInt("app_id"),rs.getString("id"), rs
									.getString("name"), 
									rs.getString("number"), 
									rs.getString("date"), 
									rs.getString("address"), 
									rs.getByte("completed"),
									rs.getString("assigned"),
									rs.getInt("make"),
									rs.getString("model"),
									rs.getInt("problem")	,rs.getString("track")								);
						});	
			}
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getTotalCount(int filter) throws IOException {
		try
		{
			if(filter==0){
			return template.queryForObject(COUNT_ALL,
					(rs, rownum) -> {
						return rs.getInt(1);
					});
			}
			else
			{
				return template.queryForObject(COUNT_TODAY,
						(rs, rownum) -> {
							return rs.getInt(1);
						});
			}
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void update(Timestamp t, String id) throws IOException {
		try {
			 
			template.update(UPDATE, (ps) -> {
				ps.setByte(1,new Byte("1"));
				ps.setString(2, id);
				ps.setTimestamp(3, t);
			});

	} catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}
	
	@Override
	public void updatetrack(int app_id,String track) throws IOException {
		try {
			 
			template.update(UPDATEtrack, (ps) -> {
				ps.setString(1, track);
				ps.setInt(2, app_id);
			});

	} catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}
	
	
	
	@Override
	public List<ServiceDto> gettoday(){
		return template.query(
				FETCH_TODAY2,
				(rs, rownum) -> {
					return new ServiceDto(rs.getInt("app_id"),rs.getString("id"), rs
							.getString("name"), 
							rs.getString("number"), 
							rs.getString("date"), 
							rs.getString("address"), 
							rs.getByte("completed"),
							rs.getString("assigned"),
							rs.getInt("make"),
							rs.getString("model"),
							rs.getInt("problem"),rs.getString("track")									);
				});	
	}

	@Override
	public void cancel(int app_id)
	{
		template.update(CANCEL,(ps)->{
			ps.setByte(1, new Byte("2"));
			ps.setInt(2,app_id);
		});
	}

	@Override
	public void updateSRE(int app_id, String sre_id) throws IOException {
		try {
			 
			template.update(UPDATESRE, (ps) -> {
				ps.setString(1, sre_id);
				ps.setInt(2, app_id);
			});

	} catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}
	

	@Override
	public void update(int id) {
		template.update(UPDATE2, (ps) -> {
			ps.setInt(1, id);
		});
		
	}
	@Override
	public int getSRECount(String id) throws IOException {
		try
		{
			return template.queryForObject(GETSRE,
					(rs, rownum) -> {
						return rs.getInt(1);
					},id);
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	
}
