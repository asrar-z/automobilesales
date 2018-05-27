package com.car.dao.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.SaleDao;
import com.car.dao.spec.VehicleDao;
import com.car.dto.OfficeDto;
import com.car.dto.SaleDto;
import com.car.entity.LineEntity;

@Repository
public class SaleDaoImpl implements SaleDao{
	@Autowired
	private JdbcTemplate template;
	@Autowired VehicleDao v;
	private static final String INSERT="INSERT INTO SALES_RECORD"
			+ " (id, customer_firstname, customer_lastname, vehicle_id,emi,total_amount,officeid,profit,year)"
			+ " VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
	private static final String FLAG="UPDATE CUSTOMERS SET emi=1,category=? WHERE id=? ";
	private static final String LINE="SELECT year, COUNT(*) FROM sales_record WHERE vehicle_id= ? GROUP BY year";
	private static final String PROFIT="SELECT * FROM SALES_RECORD where officeid=? AND emi=1 LIMIT ? OFFSET ?";
	private static final String SALES="SELECT * FROM SALES_RECORD WHERE emi=1 LIMIT ? OFFSET ?";
	private static final String COUNT="SELECT COUNT(*) FROM SALES_RECORD WHERE emi=1";
	private static final String FILTERED_COUNT="SELECT COUNT(*) FROM SALES_RECORD WHERE emi=1 AND officeid=?";
	private static final String GET_SALES_FOR_VEHICLE="SELECT COUNT(*) FROM SALES_RECORD WHERE vehicle_id=? AND officeid=? AND year=?";
	@Override
	public void insert(SaleDto sale)throws IOException{
		try {
			
			template.update(INSERT, (ps) -> {
				ps.setString(1, sale.getId());
				ps.setString(2,sale.getCustomer_firstname());
				ps.setString(3, sale.getCustomer_lastname());
				ps.setString(4, sale.getVehicle_id());
				ps.setInt(5, sale.getEmi());
				ps.setString(6,sale.getTotal_amount() );
				ps.setInt(7,sale.getOfficeid() );
				ps.setString(8,sale.getProfit() );
				ps.setString(9,sale.getYear());
			});
			
			int category=v.getCategory(sale.getVehicle_id(), sale.getOfficeid());
			template.update(FLAG,(ps)->{	ps.setInt(1,category);ps.setString(2, sale.getId());
		
			});
			
			
			
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}
	@Override
	public List<LineEntity> getSalesById(String vid) throws IOException {
try
{
	
	return template.query(LINE, ps-> {	ps.setString(1, vid);
	},(rs, rownum) -> {
		return new LineEntity(rs.getString("year"),rs.getInt(2));
	});
	
	
	
}catch (DataAccessException e) {
	throw new IOException(e);
}
	}
	@Override
	public List<SaleDto> getSaleByOffice(int officeid,int start,int size) throws IOException {
		try
		{
		System.out.println("Debug : OfficeID called" + officeid);	
			return template.query(PROFIT, ps-> {
				ps.setInt(1, officeid);
				ps.setInt(2,size);
				ps.setInt(3,start);
			},(rs, rownum) -> {
				return new SaleDto(rs.getInt("saleid"),
						rs.getString("id"),
						rs.getString("customer_firstname"),
						rs.getString("customer_lastname"),
						rs.getString("vehicle_id"),
						rs.getInt("emi"),
						rs.getString("total_amount"),
						rs.getInt("officeid"),
						rs.getString("profit"),
						rs.getString("year"));
			});
			
			
			
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	@Override
	public List<SaleDto> getAll(int start,int size) throws IOException {
		try
		{
			
			return template.query(SALES,ps -> {
				ps.setInt(1, size);
				ps.setInt(2, start);
			},(rs, rownum) -> {
				return new SaleDto(rs.getInt("saleid"),
						rs.getString("id"),
						rs.getString("customer_firstname"),
						rs.getString("customer_lastname"),
						rs.getString("vehicle_id"),
						rs.getInt("emi"),
						rs.getString("total_amount"),
						rs.getInt("officeid"),
						rs.getString("profit"),
						rs.getString("year"));
			});
			
			
			
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	@Override
	public int getTotalCount() throws IOException {
		return template.queryForObject(COUNT, (rs, rownum) -> {
			return rs.getInt(1);
		});
		
	}
	@Override
	public int getFilteredTotalCount(int officeid) throws IOException {
try
{
	
	return template.queryForObject(FILTERED_COUNT,
			(rs, rownum) -> {
				return rs.getInt(1);
			}, officeid);
} catch (DataAccessException e) {
throw new IOException(e);
}
}
	@Override
	public int getrecords(String id, int office, String year) {
		return template.queryForObject(GET_SALES_FOR_VEHICLE, (rs, rownum) -> {
			return rs.getInt(1);
		},id,office,year);
	}

	
	
	
	

}
