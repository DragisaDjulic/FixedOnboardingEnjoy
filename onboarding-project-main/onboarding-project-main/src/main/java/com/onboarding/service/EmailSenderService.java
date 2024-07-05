package com.onboarding.service;

public interface EmailSenderService {
	
	public void sendEmail(String toEmail,String subject,String body);

	public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment);

}
