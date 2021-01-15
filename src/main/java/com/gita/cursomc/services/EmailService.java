package com.gita.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gita.cursomc.domain.Cliente;
import com.gita.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

	void sendNewPasswordEmail(Cliente cliente, String newPass);
}