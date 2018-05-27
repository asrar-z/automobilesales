package com.car.dao.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car.dao.spec.FeedbackDao;
import com.car.dto.CustomersDto;
import com.car.dto.FeedbackDto;
import com.car.entity.FeedbackEntity;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

	@Autowired
	private JdbcTemplate template;
	
	private final static String INSERT="INSERT INTO FEEDBACK"
			+ " (firstname, lastname, email, subject, message)"
			+ " VALUES (?, ?, ?, ?, ?)";
			
			private final static String GET="SELECT * FROM FEEDBACK";
			
	
	@Override
	public void insert(FeedbackDto feedbackDto) throws IOException {
		try
		{
			template.update(INSERT, (ps) -> {
				ps.setString(1,feedbackDto.getFirstname());
				ps.setString(2,feedbackDto.getLastname());
				ps.setString(3,feedbackDto.getEmail());
				ps.setString(4,feedbackDto.getSubject());
				ps.setString(5,feedbackDto.getMessage());
			});
			
		}
		catch(DataAccessException e)
		{
			throw new IOException(e);
		}
		
	}




	@Override
	public List<FeedbackDto> getAll() throws IOException {
		try
		{
			return template.query(
					GET,
					(rs, rownum) -> {
						return new FeedbackDto(
								rs.getString("firstname"),
								rs.getString("lastname"),
						rs.getString("email"),
						rs.getString("subject"),
						rs.getString("message"));
					});
			
		}
		catch (DataAccessException e) {
			throw new IOException(e);
		}
	}

}
