package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.UserDao;
import com.car.dto.UserAccountDto;

@Repository
public class UserDaoImpl implements UserDao{
	@Autowired
	private JdbcTemplate template;
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	private static final String FETCH_BY_ID = "SELECT * FROM USER_ACCOUNTS WHERE id = ?";
	private static final String FETCH = "SELECT * FROM USER_ACCOUNTS LIMIT ? OFFSET ?";
	private static final String FETCH_BY_ROLE = "SELECT * FROM USER_ACCOUNTS WHERE roleid = ? LIMIT ? OFFSET ?";
	private static final String INSERT_USER = "INSERT INTO USER_ACCOUNTS "
			+ " (id, password, firstname, lastname, email, roleid)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_USER = "UPDATE USER_ACCOUNTS SET password = ?, "
			+ " firstname = ?, lastname = ?, email = ?, roleid = ? WHERE id = ?";


	
	private static final String DELETE_USER = "DELETE FROM USER_ACCOUNTS WHERE id = ?";
	
	
	private static final String USER_COUNT_BY_OFFICE = "SELECT COUNT(*) FROM USER_ACCOUNTS WHERE roleid = ?";
	
	private static final String USER_COUNT = "SELECT COUNT(*) FROM USER_ACCOUNTS";
	
	
	
	
	
	
	private static final String USER_FILTER_BY_SEARCH = "SELECT * FROM USER_ACCOUNTS WHERE id LIKE ? OR firstname LIKE ? OR lastname LIKE ? OR email LIKE ? "
			+ "OR id IN  (SELECT id FROM USER_ROLES WHERE role LIKE ?) "
			+ "LIMIT ? OFFSET ?";
	private static final String USER_FILTER_COUNT = "SELECT COUNT(*) FROM USER_ACCOUNTS WHERE LOWER(id) LIKE ? OR LOWER(firstname) LIKE ? OR lastname LIKE ? OR email LIKE ? "
			+ "OR id IN  (SELECT id FROM USER_ROLES WHERE role LIKE ?) ";	
	
	
	
	@Override
	public UserAccountDto getBy(String id) throws IOException {
		try {
			return template.queryForObject(
					FETCH_BY_ID,
					(rs, rownum) -> {
						return new UserAccountDto(rs.getString("id"), 
								rs.getString("password"), 
								rs.getString("firstname"), 
								rs.getString("lastname"),
								rs.getString("email"),
								rs.getInt("roleid"));
					}, id);
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<UserAccountDto> getBy(int roleId, int start, int size) throws IOException {
		// TODO Auto-generated method stub
		try {
			if (roleId <= 0) {
				return template.query(
						FETCH,
						ps -> {
							ps.setInt(1, size);
							ps.setInt(2, start);
						},
						(rs, rownum) -> {
							return new UserAccountDto(rs.getString("id"), rs
									.getString("password"), rs
									.getString("firstname"), rs
									.getString("lastname"), rs.getString("email"),
									rs.getInt("roleid"));
						});
			} else {
				return template.query(
					FETCH_BY_ROLE,
					ps -> {
						ps.setInt(1, roleId);
						ps.setInt(2, size);
						ps.setInt(3, start);
					},
					(rs, rownum) -> {
						return new UserAccountDto(rs.getString("id"), rs
								.getString("password"), rs
								.getString("firstname"), rs
								.getString("lastname"), rs.getString("email"),
								rs.getInt("roleid"));
					});
			}
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void insert(UserAccountDto userAccount) throws IOException {
		try {
			
			logger.debug( userAccount.getUserid()+userAccount.getPassword()+userAccount.getFirstName()+userAccount.getLastName()+userAccount.getEmail());
			
			template.update(INSERT_USER, (ps) -> {
				ps.setString(1, userAccount.getUserid());
				ps.setString(2, userAccount.getPassword());
				ps.setString(3, userAccount.getFirstName());
				ps.setString(4, userAccount.getLastName());
				ps.setString(5, userAccount.getEmail());
				ps.setInt(6, userAccount.getRoleid());
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public void update(UserAccountDto userAccount) throws IOException {
		try {
			 
				template.update(UPDATE_USER, (ps) -> {
					ps.setString(1, userAccount.getPassword());
					ps.setString(2, userAccount.getFirstName());
					ps.setString(3, userAccount.getLastName());
					ps.setString(4, userAccount.getEmail());
					ps.setInt(5, userAccount.getRoleid());
					ps.setString(6, userAccount.getUserid());
				});

		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public void deleteBy(String id) throws IOException {
		try {
			template.update(DELETE_USER, (ps) -> ps.setString(1, id));
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public int getTotalCount(int roleId) throws IOException {
		// TODO Auto-generated method stub
		try {
			if (roleId <= 0) {
				return template.queryForObject(USER_COUNT,
						(rs, rownum) -> {
							return rs.getInt(1);
						});
			} else {
				return template.queryForObject(USER_COUNT_BY_OFFICE,
						(rs, rownum) -> {
							return rs.getInt(1);
						}, roleId);
			}
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

	@Override
	public List<UserAccountDto> filter(String searchParam, int start, int size)
			throws IOException {
		try{
			return template.query(
					USER_FILTER_BY_SEARCH,
					ps -> {
						ps.setString(1, "%" + searchParam + "%");
						ps.setString(2, "%" + searchParam + "%");
						ps.setString(3, "%" + searchParam + "%");
						ps.setString(4, "%" + searchParam + "%");
						ps.setString(5, "%" + searchParam + "%");
						ps.setInt(6, size);
						ps.setInt(7, start);
					},
					(rs, rownum) -> {
						return new UserAccountDto(rs.getString("id"), rs
								.getString("password"), rs
								.getString("firstname"), rs
								.getString("lastname"), rs.getString("email"),
								rs.getInt("roleid"));
					});
		}catch (DataAccessException e) {
			throw new IOException(e);
		}
		
	}

	@Override
	public int getFilteredCount(String searchParam) throws IOException {
		try {
			return template.queryForObject(USER_FILTER_COUNT, (rs, rownum) -> {
				return rs.getInt(1);
			}, "%" + searchParam + "%", "%" + searchParam + "%", "%" + searchParam + "%", "%" + searchParam + "%", "%" + searchParam + "%");
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}


	
	
}
