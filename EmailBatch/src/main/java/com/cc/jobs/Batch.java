package com.cc.jobs;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cc.jobs.dao.SendEmailDAO;
import com.cc.jobs.domain.SendEmail;
import com.cc.jobs.service.SendEmailService;

public class Batch {
	
	private static SendEmailDAO sendmaildao;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ApplicationContext context = new
	    		 ClassPathXmlApplicationContext("classpath:spring_config.xml");
		
		sendmaildao = (SendEmailDAO)context.getBean("SendEmailDAO");
		System.out.println("Jobs Started");
		while(true) {
			try {
				List<SendEmail> sendemail = sendmaildao.find_email("yet_to_send");
				
				for(int i = 0; i< sendemail.size(); i++) {
					SendEmailService emailservice = new SendEmailService(sendemail.get(i));
					emailservice.start();
				}
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
