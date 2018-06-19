package com.cc.jobs.domain;

public class SendEmail {

	private int id;
	private String email_content;
	private String receiver_mail;
	private String sender_mail;
	private String status;
	private String subject;
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail_content() {
		return email_content;
	}
	public void setEmail_content(String email_content) {
		this.email_content = email_content;
	}
	public String getReceiver_mail() {
		return receiver_mail;
	}
	public void setReceiver_mail(String receiver_mail) {
		this.receiver_mail = receiver_mail;
	}
	public String getSender_mail() {
		return sender_mail;
	}
	public void setSender_mail(String sender_mail) {
		this.sender_mail = sender_mail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
