package com.car.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.EMIDao;
import com.car.dao.spec.SMNotificationDao;
import com.car.dto.EMIDto;
import com.car.dto.SMNotificationDto;
@Repository
public class EMIDaoImpl implements EMIDao{
	@Autowired
	private JdbcTemplate template;
	@Autowired SMNotificationDao n;
	private final static String FETCH_BY_CID="SELECT * FROM EMI WHERE c_id = ? ";
	private final static String EMI_CUSTOMERS="SELECT * FROM EMI";	
	private final static String INSERT="INSERT INTO EMI"
			+ " (c_id, firstname, lastname, total, duration,rate,office,status,mail)"
			+ " VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
	private final static String getstatus="SELECT status from emi where c_id=?";
	private final static String updatestatus="UPDATE emi SET status=? where c_id=?";
	private final static String updatemonth="UPDATE emi set month=? where id=?";
	@Override
	public EMIDto getBy(String c_id) throws IOException {

			try
			{
				return template.queryForObject(FETCH_BY_CID, (rs, rownum) -> {
					return new EMIDto(rs.getInt("id"),rs.getString("c_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("total"),rs.getString("duration"),rs.getString("rate"),rs.getInt("office"),rs.getString("status"),rs.getString("mail"),rs.getInt("month"));
				},c_id);
				
			}catch(DataAccessException e) {
				throw new IOException(e);
			}
			
	}


	@Override
	public List<EMIDto> getemi() throws IOException {
		try
		{
			return template.query(EMI_CUSTOMERS, (rs, rownum) -> {
				return new EMIDto(rs.getInt("id"),rs.getString("c_id"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("total"),rs.getString("duration"),rs.getString("rate"),rs.getInt("office"),rs.getString("status"),rs.getString("mail"),rs.getInt("month"));
			});
			
		}catch(DataAccessException e) {
			throw new IOException(e);
		}

	}


	@Override
	public void insert(EMIDto sale) throws IOException {
		List<String> l=new ArrayList<String>();
		String str = sale.getDuration().replaceAll("\\D+","");
		int dur=Integer.parseInt(str);
		System.out.println(dur);
		for(int i=0;i<dur;i++)
		{
			l.add("pending");
		}
		System.out.println(l.toString());
		List<String> ll=new ArrayList<String>();
		ll=Arrays.asList(l.toString().replace("[", "").replace("]","").replace(" ","").split(","));
		System.out.println(ll);
		System.out.println(ll.get(5));
		try
		{
			template.update(INSERT,(ps)->{
				
				ps.setString(1,sale.getC_id());
				ps.setString(2,sale.getFirstname());
				ps.setString(3,sale.getLastname());
				ps.setString(4,sale.getTotal());
				ps.setString(5,sale.getDuration());
				ps.setString(6,sale.getRate());
				ps.setInt(7, sale.getOffice());
				ps.setString(8,l.toString());
				ps.setString(9, sale.getEmail());
				
				
				
				
			});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}


	@Override
	public void update(String c_id, String idx) throws IOException {
		
		String old;
		try {
			old=template.queryForObject(getstatus,
					(rs, rownum) -> {
						return String.valueOf(rs.getString("status"));
					}, c_id);
			
			List<String> al=Arrays.asList(old.toString().replace("[", "").replace("]","").replace(" ","").split(","));
			int i=Integer.parseInt(idx)-1;
			al.set(i, "paid");
			template.update(updatestatus, (ps) -> {
				
				ps.setString(1,al.toString());
				ps.setString(2, c_id);
				
				
			});
			
			n.notify(new SMNotificationDto(c_id,"New Payment",c_id+" made a new payment"));
			
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}
	@Override
	public void update_month(int month,int id)
	{
		template.update(updatemonth,(ps)->{
			ps.setInt(1, month);
			ps.setInt(2, id);
		});
	}

}
