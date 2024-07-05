package com.onboarding.service.implementation;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.onboarding.service.EmailSenderService;


@Service
public class EmailSenderServiceImplementation implements EmailSenderService{

	@Value("${spring.mail.username}")
	private String email;
	
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toWhichEmail,String subject,String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(email);
		message.setTo(toWhichEmail);
		message.setText(body);
		message.setSubject(subject);
		
		
		mailSender.send(message);
		
		System.out.println("Mail Sent successfully...");
	}
	
	
	@Override
	public void sendMessageWithAttachment(
	  String to, String subject, String text, String pathToAttachment) {
	    // ...
	    
	    MimeMessage message = 
	    		mailSender.createMimeMessage();
	     
	    MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			
			helper.setFrom("noreply@baeldung.com");
		    helper.setTo(to);
		    helper.setSubject(subject);
		    helper.setText(text);
		    
		    FileSystemResource file 
		      = new FileSystemResource(new File(pathToAttachment));
		    helper.addAttachment("Report.pdf", file);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    

//	    mailSender.send(message);
	    
//	    System.out.println("Mail Sent successfully...");
	    
	    // ...
	}
	
	
}





