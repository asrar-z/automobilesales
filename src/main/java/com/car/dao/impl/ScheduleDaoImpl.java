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

import com.car.dao.spec.ScheduleDao;
import com.car.dto.ScheduleDto;


@Repository
public class ScheduleDaoImpl implements ScheduleDao{

	
	
	
	@Autowired
	private JdbcTemplate template;
	
	
	private static final String INSERT_SCHEDULE = "INSERT INTO SALES_APPOINTMENTS"
			+ " (id, name, number, date, completed,officeid)"
			+ " VALUES (?, ?, ?, ?, ?,?)";
	private static final String FETCH_BY_ID="SELECT * FROM SALES_APPOINTMENTS WHERE id = ? LIMIT ? OFFSET ?";
	private static final String FETCH="SELECT * FROM SALES_APPOINTMENTS LIMIT ? OFFSET ?";
	private static final String FETCH_TODAY="SELECT * FROM SALES_APPOINTMENTS WHERE DATE(date)=CURDATE() LIMIT ? OFFSET ?";
	private static final String FETCH_TODAY2="SELECT * FROM SALES_APPOINTMENTS WHERE DATE(date)=CURDATE() ";
	private static final String COUNT="SELECT COUNT(*) FROM SALES_APPOINTMENTS WHERE id = ? ";
	private static final String COUNT_ALL="SELECT COUNT(*) FROM SALES_APPOINTMENTS";
	private static final String COUNT_TODAY="SELECT COUNT(*) FROM SALES_APPOINTMENTS WHERE DATE(date)=CURDATE() ";
	private static final String UPDATE = "UPDATE SALES_APPOINTMENTS SET completed = ? WHERE id = ? AND date=?";
	private static final String UPDATE2 = "UPDATE SALES_APPOINTMENTS SET completed = ? WHERE app_id = ? ";
	private static final String UPDATE_SE="UPDATE SALES_APPOINTMENTS SET assigned=? WHERE app_id=?";
	private static final String GETSE="SELECT COUNT(*) FROM sales_appointments where assigned=? AND DATE(date)=CURDATE() ";
	@Override
	public ScheduleDto getBy(String id) throws IOException {
		// TODO Auto-generated method stub
		return null;
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
	public void insert(ScheduleDto userSchedule) throws IOException, ParseException {
		 
		
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    java.util.Date parsedDate = dateFormat.parse(userSchedule.getDate());
		    
		    System.out.println("Asrar Date:" + parsedDate);
		    
		    Timestamp  timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    System.out.println("Asrar Date:" + timestamp);
		try{
			template.update(INSERT_SCHEDULE, (ps) -> {
				ps.setString(1, userSchedule.getId());
				ps.setString(2, userSchedule.getName());
				ps.setString(3, userSchedule.getNumber());
				ps.setTimestamp(4, timestamp);
				ps.setByte(5, userSchedule.getCompleted());
			    ps.setInt(6, userSchedule.getOfficeid());
			});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void update(Timestamp date, String id) throws IOException {
		
		try {
			 
			template.update(UPDATE, (ps) -> {
				ps.setByte(1,new Byte("1"));
				ps.setString(2, id);
				ps.setTimestamp(3, date);
			});

	} catch (DataAccessException e) {
		throw new IOException(e);
	}
	}

	@Override
	public List<ScheduleDto> getBy(String id, int start, int size)	throws IOException {
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
						return new ScheduleDto(rs.getInt("app_id"),rs.getString("id"), rs
								.getString("name"), 
								rs.getString("number"), 
								rs.getString("date"), 
								rs.getByte("completed"),rs.getString("assigned"),rs.getInt("officeid"));
					});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public List<ScheduleDto> getAll(int filter,int start, int size) throws IOException {
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
						return new ScheduleDto(rs.getInt("app_id"),rs.getString("id"), rs
								.getString("name"), 
								rs.getString("number"), 
								rs.getString("date"), 
								rs.getByte("completed"),rs.getString("assigned"),rs.getInt("officeid"));
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
							return new ScheduleDto(rs.getInt("app_id"),rs.getString("id"), rs
									.getString("name"), 
									rs.getString("number"), 
									rs.getString("date"), 
									rs.getByte("completed"),rs.getString("assigned"),rs.getInt("officeid"));
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
				{return template.queryForObject(COUNT_TODAY,
						(rs, rownum) -> {
							return rs.getInt(1);
						});
				}
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getSECount(String id) throws IOException {
		try
		{
			return template.queryForObject(GETSE,
					(rs, rownum) -> {
						return rs.getInt(1);
					},id);
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	
	
	@Override
	public List<ScheduleDto> gettoday() throws IOException {
	try{
		
		return template.query(
				FETCH_TODAY2,
				(rs, rownum) -> {
					return new ScheduleDto(rs.getInt("app_id"),rs.getString("id"), rs
							.getString("name"), 
							rs.getString("number"), 
							rs.getString("date"), 
							rs.getByte("completed"),
							rs.getString("assigned"),rs.getInt("officeid"));
				});	
	
}catch (DataAccessException e) {
	throw new IOException(e);
}
	}

	@Override
	public void updateSE(int app_id, String se_id) throws IOException {
		try {
			 
			template.update(UPDATE_SE, (ps) -> {
				ps.setString(1, se_id);
				ps.setInt(2, app_id);
			});

	} catch (DataAccessException e) {
		throw new IOException(e);
	}
		
	}

	@Override
	public void update(int id,Byte b) {
		template.update(UPDATE2, (ps) -> {
			ps.setByte(1,b);
			ps.setInt(2, id);
		});
		
	}

}
