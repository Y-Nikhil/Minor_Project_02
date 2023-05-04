package com.ashokit.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class emailSender {

	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to, String subject, String body) throws MessagingException {
		
		try {
			
			MimeMessage message= mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setFrom("yarranarayana2000@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			
			mailSender.send(message);
			System.out.println("Mail sending Successfull...");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
