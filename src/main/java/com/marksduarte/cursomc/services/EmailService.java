package com.marksduarte.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.marksduarte.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
