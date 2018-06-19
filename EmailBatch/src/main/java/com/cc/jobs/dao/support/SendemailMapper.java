package com.cc.jobs.dao.support;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cc.jobs.domain.SendEmail;

public class SendemailMapper implements RowMapper<SendEmail> {

	@Override
	public SendEmail mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SendEmail email = new SendEmail();
		email.setEmail_content(rs.getString("email_content"));
		email.setReceiver_mail(rs.getString("receiver_mail"));
		email.setSender_mail(rs.getString("sender_mail"));
		email.setStatus(rs.getString("status"));
		email.setSubject(rs.getString("subject"));
		email.setId(rs.getInt("id"));
		
		return email;
	}

}
