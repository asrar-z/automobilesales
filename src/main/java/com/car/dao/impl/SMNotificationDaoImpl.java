package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.SMNotificationDao;
import com.car.dto.SMNotificationDto;
import com.car.entity.SMNotificationEntity;

@Repository

public class SMNotificationDaoImpl implements SMNotificationDao {

	@Autowired
	private JdbcTemplate template;
	
	private final static String GET="SELECT * FROM smnotifications ";
	private final static String COUNT="SELECT COUNT(*) FROM smnotifications";
	private final static String NOTIFY="INSERT INTO smnotifications"
			+"(c_id,subject,message)"
			+"VALUES (?,?,?)";
	private final static String prev="SELECT prev_count from SMnotcount";
	private final static String update_prev="UPDATE SMnotcount set prev_count=?";
	private final static String getall="SELECT * FROM smnotifications";
	@Override
	public SMNotificationEntity get() throws IOException {
		try{
			
			int count=template.queryForObject(COUNT,
					(rs,rownum)->{
						return rs.getInt(1);
					});
			List<SMNotificationDto> l=null;
			l=template.query(GET,(rs,rownum)->{
				return new SMNotificationDto(
				rs.getString("c_id"),
				rs.getString("subject"),
				rs.getString("message"));
			});
			return new SMNotificationEntity(count,l);
			
		}catch(DataAccessException e)
		{
			throw new IOException(e);
		}
		
	}
	@Override
	public void notify(SMNotificationDto n) throws IOException {
		System.out.println("Inserting in smnot");
		try{
			template.update(NOTIFY,(ps)->{
				ps.setString(1, n.getC_id());
				ps.setString(2, n.getSubject());
				ps.setString(3,n.getMessage());
			});
			
		}catch(DataAccessException e){
			throw new IOException(e);
		}
		
	}
	@Override
	public int getprevC() {
		return template.queryForObject(prev,(rs,rownum)->{
			return rs.getInt("prev_count");
		});
	}
	@Override
	public void updateprev(int count)
	{
		template.update(update_prev,(ps)->{
			ps.setInt(1, count);
		});
	}
	
	@Override
	public List<SMNotificationDto> getall() throws IOException
	{
		try
		{
			return template.query(getall,(rs,rownum)->{
				return new SMNotificationDto(rs.getString("c_id"),rs.getString("subject"),rs.getString("message"));
			});
		}catch(DataAccessException e){
			throw new IOException(e);
		}
	}

}
