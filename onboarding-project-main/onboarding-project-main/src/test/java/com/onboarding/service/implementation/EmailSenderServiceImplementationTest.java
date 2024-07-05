package com.onboarding.service.implementation;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailSenderServiceImplementationTest {

    @InjectMocks
    private EmailSenderServiceImplementation emailSenderService;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Value("${spring.mail.username}")
    private String username;

    @Test
    public void testSendEmail() {
        String toEmail = username;
        String subject = "Test Subject";
        String body = "Test Body";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        emailSenderService.sendEmail(toEmail, subject, body);

        verify(mailSender, times(1)).send(message);
    }

    @Test
    public void testSendMessageWithAttachment() throws Exception {
        String toEmail = username;
        String subject = "Test Subject";
        String body = "Test Body";
        String pathToAttachment = "path/to/attachment.pdf";

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        FileSystemResource fileResource = new FileSystemResource(new File(pathToAttachment));
        emailSenderService.sendMessageWithAttachment(toEmail, subject, body, pathToAttachment);

        verify(mailSender, times(1)).createMimeMessage();
        verify(mailSender, times(1)).send(mimeMessage);
    }
}