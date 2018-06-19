package com.cc.jobs.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cc.jobs.dao.support.SendemailMapper;
import com.cc.jobs.domain.SendEmail;

@Repository("SendEmailDAO")
public class SendEmailDAO {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;

		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Value("${find_email_yet_to_send.sql}")
	private String find_email_yet_to_send;
	
	@Value("${update_email_status.sql}")
	private String update_email_status;
	
	@Value("${delete_email.sql}")
	private String delete_email;

	public List<SendEmail> find_email(String status) {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("status", status);
		SendemailMapper mapper = new SendemailMapper();
		List<SendEmail> mails = this.namedParameterJdbcTemplate.query(find_email_yet_to_send, parameters, mapper);

		if (mails.size() < 1) {
			System.out.println("There is no mail to send");
		}
		return mails;
	}

	public void updatemailstatus(int id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("status", "sent");
		parameters.addValue("id", id);
		this.namedParameterJdbcTemplate.update(update_email_status, parameters);
		
	}

	public void deletemail(int id) {
		// TODO Auto-generated method stub
		MapSqlParameterSource parameters;
		parameters = new MapSqlParameterSource();
		parameters.addValue("status", "sent");
		this.namedParameterJdbcTemplate.update(delete_email, parameters);
		
	}

}
