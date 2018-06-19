package com.cc.jobs.service;

import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cc.jobs.dao.SendEmailDAO;
import com.cc.jobs.domain.SendEmail;

public class SendEmailService implements Runnable {

	private Thread t;
	private SendEmail email;
	private SendEmailDAO sendemaildao;
	private ApplicationContext context;
	ResourceBundle bundle = ResourceBundle.getBundle("jdbc"); 

	public SendEmailService(SendEmail sendEmail) throws AddressException, MessagingException {
		// TODO Auto-generated constructor stub
		this.email = sendEmail;
	}

	public void run() {
		System.out.println("Started to send Mail");
		context = new ClassPathXmlApplicationContext("classpath:spring_config.xml");
		sendemaildao = (SendEmailDAO) context.getBean("SendEmailDAO");
		// For gmail

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "compliancecompendium.co.uk");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(/*email.getSender_mail()*/bundle.getString("email.username"), bundle.getString("email.password"));
			}
		});

		try {

			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(/*email.getSender_mail()*/bundle.getString("email.username")));
			InternetAddress addressTo = new InternetAddress(email.getReceiver_mail());
			msg.setRecipient(RecipientType.TO, addressTo);
			msg.setSubject(email.getSubject());
			BodyPart messageBodyPart = new MimeBodyPart();
			ArrayList<String> content = new ArrayList<String>();
			content.add(email.getEmail_content());
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			StringBuilder builder = new StringBuilder();
			for (String value : content) {
				builder.append(value);
			}
			String finalTemplate = builder.toString();
			msg.setContent(finalTemplate, "text/html");
			messageBodyPart = new MimeBodyPart();
			multipart.addBodyPart(messageBodyPart);
			if (finalTemplate.isEmpty() == false) {
				System.out.println("Before E-mail sent !"+email.getSender_mail());
				try {
					Transport.send(msg);
				}catch(Exception ex) {
					System.out.println("E-mail sent Exception"+ex.getMessage());
				}
				System.out.println("E-mail sent !");
				sendemaildao.updatemailstatus(email.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void start() throws InterruptedException {
		System.out.println("Sending Email For " + email.getReceiver_mail());
		if (t == null) {
			t = new Thread(this);
			t.start();
		}
	}

}
