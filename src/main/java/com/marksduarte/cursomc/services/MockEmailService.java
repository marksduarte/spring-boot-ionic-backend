package com.marksduarte.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MockEmailService extends AbstractEmailService {
		
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
		
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando e-mail...");
		LOG.info(msg.toString());
		LOG.info("Email enviado.");		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando e-mail HTML...");
		LOG.info(msg.toString());
		LOG.info("Email enviado.");			
	}

}
