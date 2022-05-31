package com.residencia.comercio.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	JavaMailSender emailSender;
	
	
	
	@Value("${spring.mail.host}")	
	private String mailHost;
	
	@Value("${spring.mail.port}")	
	private String mailPort;
	
	@Value("${spring.mail.username}")	
	private String mailUsername;
	
	@Value("${spring.mail.password}")	
	private String mailPassword;

	
	public MailService(JavaMailSender javaMailSender) {
		this.emailSender = emailSender;
	}
	
	public void enviarEmailTexto(String destinatarioEmail, String assunto, String mensagemEmail) {
		SimpleMailMessage sMailMessage = new SimpleMailMessage();
		
		sMailMessage.setTo(destinatarioEmail);
		sMailMessage.setSubject(assunto);
		sMailMessage.setText(mensagemEmail);
		
		sMailMessage.setFrom("teste@teste.com");
		
		emailSender.send(sMailMessage);
	}
	
	public void enviarEmailTextoHtml(String destinatarioEmail, String assunto) throws MessagingException {

        MimeMessage mail = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mail);
        helper.setTo(destinatarioEmail);
        helper.setSubject( assunto );
        helper.setText("<h1>Hello from Spring Boot Application</h1>", true);
        emailSender.send(mail);


}
	
	
	
}
