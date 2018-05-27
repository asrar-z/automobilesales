package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.CustomerDao;
import com.car.dto.CustomersDto;
import com.car.dto.UserAccountDto;



@Repository
public class CustomerDaoImpl implements CustomerDao{
	
	@Autowired
	private JdbcTemplate template;
	private static final String INSERT_CUSTOMER = "INSERT INTO CUSTOMERS "
			+ " (id, firstname, lastname, email)"
			+ " VALUES (?, ?, ?, ?)";
	private static final String FETCH = "SELECT * FROM CUSTOMERS LIMIT ? OFFSET ?";
	private static final String CUSTOMERS_TABLE_SIZE = "SELECT COUNT(*) FROM CUSTOMERS";
	
	private static final String DELETE_CUSTOMER = "DELETE FROM CUSTOMERS WHERE id = ?";
	
	
	private static final String CUSTOMER_FILTER_BY_SEARCH = "SELECT * FROM CUSTOMERS WHERE id LIKE ? OR firstname LIKE ? OR lastname LIKE ? OR email LIKE ? "
			+ "LIMIT ? OFFSET ?";
	private static final String CUSTOMER_FILTER_COUNT = "SELECT COUNT(*) FROM USER_ACCOUNTS WHERE LOWER(id) LIKE ? OR LOWER(firstname) LIKE ? OR lastname LIKE ? OR email LIKE ? ";	
	
	private static final String EMAILS="SELECT email FROM CUSTOMERS where category=?";
	
	private static final String GET_ID="SELECT ID FROM CUSTOMERS";
	
	private static final String GET_BY_ID="SELECT * FROM CUSTOMERS WHERE id=?";
	
	@Override
	public CustomersDto getBy(String id) throws IOException {
		try
		{
			return template.queryForObject(
					GET_BY_ID,
					(rs, rownum) -> {
						return new CustomersDto(
								rs.getString("id"),
								rs.getString("firstname"),
								rs.getString("lastname"), 
								rs.getString("email"),
								rs.getByte("emi"));
					},id);
			
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<CustomersDto> getBy(int start, int size) throws IOException {
		// TODO Auto-generated method stub
		try
		{
			return template.query(
					FETCH,
					ps -> {
						ps.setInt(1, size);
						ps.setInt(2, start);
					},
					(rs, rownum) -> {
						return new CustomersDto(
								rs.getString("id"),
								rs.getString("firstname"),
								rs.getString("lastname"), 
								rs.getString("email"),
								rs.getByte("emi"));
					});
			
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public void insert(CustomersDto customerAccount) throws IOException {
		try {
			
			//logger.debug( customerAccount.getUserid()+customerAccount.getPassword()+customerAccount.getFirstName()+customerAccount.getLastName()+customerAccount.getEmail());
			
			template.update(INSERT_CUSTOMER, (ps) -> {
				ps.setString(1, customerAccount.getUserid());
				ps.setString(2, customerAccount.getFirstName());
				ps.setString(3, customerAccount.getLastName());
				ps.setString(4, customerAccount.getEmail());
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}
		
	

	@Override
	public void update(CustomersDto customerAccount) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBy(String id) throws IOException {
		try {
			template.update(DELETE_CUSTOMER, (ps) -> ps.setString(1, id));
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public int getTotalCount() throws IOException {
		// TODO Auto-generated method stub
		{
			return template.queryForObject(CUSTOMERS_TABLE_SIZE, (rs, rownum) -> {
				return rs.getInt(1);
			});
	}

}

	@Override
	public List<CustomersDto> getBy(String searchParam, int start, int length) throws IOException {
		try{
			return template.query(
					CUSTOMER_FILTER_BY_SEARCH,
					ps -> {
						ps.setString(1, "%" + searchParam + "%");
						ps.setString(2, "%" + searchParam + "%");
						ps.setString(3, "%" + searchParam + "%");
						ps.setString(4, "%" + searchParam + "%");
						ps.setInt(5, length);
						ps.setInt(6, start);
					},
					(rs, rownum) -> {
						return new CustomersDto(rs.getString("id"), rs
								.getString("firstname"), rs
								.getString("lastname"), rs.getString("email"),
								rs.getByte("emi"));
					});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getFilteredCount(String searchParam) throws IOException{
		try {
			return template.queryForObject(CUSTOMER_FILTER_COUNT, (rs, rownum) -> {
				return rs.getInt(1);
			}, "%" + searchParam + "%", "%" + searchParam + "%", "%" + searchParam + "%", "%" + searchParam + "%");
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<String> getmailid(int category) throws IOException {
		try
		{
			return template.query(EMAILS,ps->{
				ps.setInt(1,category);
			},
					(rs, rownum) -> {
						return new String(rs.getString("email"));
					});
			
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<String> getId() {
		return template.query(GET_ID, (rs, rownum) -> {
			return rs.getString("id");
		});
	}

}