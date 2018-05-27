package com.car.dao.impl;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.EmployeeDao;
import com.car.dto.EmployeeDto;
import com.car.dto.UserAccountDto;
import com.car.entity.EmployeeEntity;
import com.car.entity.UserAccountCreationEntity;

@Repository
public class EmployeeDaoImpl implements EmployeeDao  {

	
	@Autowired
	private JdbcTemplate template;
	
	private final static String UPDATE="UPDATE EMPLOYEES SET  salary=?, officeid=? WHERE id=?";
	
	private final static String FETCH="SELECT * FROM EMPLOYEES LIMIT ? OFFSET ?";
	private final static String FETCH_ALL="SELECT * FROM EMPLOYEES WHERE  officeid=?";
	private final static String COUNT="SELECT COUNT(*) FROM EMPLOYEES";
	
	private final static String FETCH_BY_OFFICE="SELECT * FROM EMPLOYEES WHERE officeid=? LIMIT ? OFFSET ?";
	private final static String COUNT_BY_OFFICE="SELECT COUNT(*) FROM EMPLOYEES WHERE officeid=?";
	private static final String INSERT_EMPLOYEE = "INSERT INTO EMPLOYEES "
			+ " (id, firstname, lastname, email, role)"
			+ " VALUES (?, ?, ?, ?, ?)";
	private final static String GET_SE="SELECT * FROM EMPLOYEES WHERE role='SALESEXECUTIVE' and officeid=?";
	private final static String GET_SRE="SELECT * FROM EMPLOYEES WHERE role='SERVICEEXECUTIVE'";
	@Override
	public void update(EmployeeDto e) throws IOException {
		try {
			 
			template.update(UPDATE, (ps) -> {
				ps.setString(1, e.getSalary());
				ps.setInt(2, e.getOfficeid());
				ps.setString(3, e.getId());
			});

	} catch (DataAccessException e2) {
		throw new IOException(e2);
	}
		
	}
	@Override
	public List<EmployeeDto> getAll(int start, int size) throws IOException {
		try {
			return template.query(FETCH, ps -> {
				ps.setInt(1, size);
				ps.setInt(2, start);
			}, (rs, rownum) -> {
				return new EmployeeDto(rs.getString("id"), rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),rs.getInt("officeid"),rs.getString("role"),rs.getString("salary"));
			});
		} catch (DataAccessException e) {
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
	public void insert(UserAccountCreationEntity entity)throws IOException {
	
		try
		{			template.update(INSERT_EMPLOYEE, (ps) -> {
			ps.setString(1, entity.getUserid());
			ps.setString(2, entity.getFirstName());
			ps.setString(3, entity.getLastName());
			ps.setString(4, entity.getEmail());
			ps.setString(5, entity.getRoles().get(0));
		});
			
		}catch (DataAccessException e) {
			throw new IOException(e);
		}


}
	@Override
	public List<EmployeeDto> get(int officeid) throws IOException {
		try {
			return template.query(FETCH_ALL,(ps) -> {
				ps.setInt(1, officeid);},  (rs, rownum) -> {
				return new EmployeeDto(rs.getString("id"), rs.getString("firstname"),rs.getString("lastname"),rs.getString("email"),rs.getInt("officeid"),rs.getString("role"),rs.getString("salary"));
			});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	@Override
	public int getFilteredTotalCount(int officeid) throws IOException {
try
{
	
	return template.queryForObject(COUNT_BY_OFFICE,
			(rs, rownum) -> {
				return rs.getInt(1);
			}, officeid);
} catch (DataAccessException e) {
throw new IOException(e);
}
}
	@Override
	public List<EmployeeDto> getBy(int officeid, int start, int length)
			throws IOException {
		try {
			return template.query(FETCH_BY_OFFICE,
					ps -> {
						ps.setInt(1, officeid);
						ps.setInt(2, length);
						ps.setInt(3, start);
						
					},
					(rs, rownum) -> {
						return new EmployeeDto(
								rs.getString("id"),
								rs.getString("firstname"),
								rs.getString("lastname"),
								rs.getString("email"),
								rs.getInt("officeid"),
								rs.getString("role"),
								rs.getString("salary"));
					});
		} catch (DataAccessException e) {
			throw new IOException(e);
		}
	}
	@Override
	public List<EmployeeDto> getSE(int id) throws IOException {
			try
			{
				return template.query(GET_SE, (ps)->{ps.setInt(1,id);},(rs,rownum)->
				{
					return new EmployeeDto(rs.getString("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("email"),
						rs.getInt("officeid"),
						rs.getString("role"),
						rs.getString("salary"));
			});
	}	catch (DataAccessException e) {
		throw new IOException(e);
	}
}
	@Override
	public List<EmployeeDto> getSRE() throws IOException {
			try
			{
				return template.query(GET_SRE, (rs,rownum)->
				{
					return new EmployeeDto(rs.getString("id"),
						rs.getString("firstname"),
						rs.getString("lastname"),
						rs.getString("email"),
						rs.getInt("officeid"),
						rs.getString("role"),
						rs.getString("salary"));
			});
	}	catch (DataAccessException e) {
		throw new IOException(e);
	}
}
}