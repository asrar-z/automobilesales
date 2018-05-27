package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dto.WHMnotificationDto;
import com.car.entity.WHMNotificationEntity;

@Repository
public class WHMNotificationDaoImpl {
	@Autowired
	private JdbcTemplate template;
	
	private final static String GET="SELECT * FROM whmnotifications ";
	private final static String COUNT="SELECT COUNT(*) FROM whmnotifications";
	private final static String NOTIFY="INSERT INTO whmnotifications"
			+"(name,subject,message)"
			+"VALUES (?,?,?)";
	private final static String prev="SELECT prev_count from whmnotcount";
	private final static String update_prev="UPDATE whmnotcount set prev_count=?";
	
	
	
	
	
	public WHMNotificationEntity get() throws IOException {
		try{
			
			int count=template.queryForObject(COUNT,
					(rs,rownum)->{
						return rs.getInt(1);
					});
			List<WHMnotificationDto> l=null;
			l=template.query(GET,(rs,rownum)->{
				return new WHMnotificationDto(
				rs.getString("name"),
				rs.getString("subject"),
				rs.getString("message"));
			});
			return new WHMNotificationEntity(count,l);
			
		}catch(DataAccessException e)
		{
			throw new IOException(e);
		}
		
	}
	
	public void notify(WHMnotificationDto n) throws IOException {
		System.out.println("Inserting in smnot");
		try{
			template.update(NOTIFY,(ps)->{
				ps.setString(1, n.getName());
				ps.setString(2, n.getSubject());
				ps.setString(3,n.getMessage());
			});
			
		}catch(DataAccessException e){
			throw new IOException(e);
		}
		
	}
	
	public int getprevC() {
		return template.queryForObject(prev,(rs,rownum)->{
			return rs.getInt("prev_count");
		});
	}
	
	public void updateprev(int count)
	{
		template.update(update_prev,(ps)->{
			ps.setInt(1, count);
		});
	}
}
