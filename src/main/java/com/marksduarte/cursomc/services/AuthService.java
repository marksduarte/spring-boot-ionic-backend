package com.marksduarte.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.Cliente;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	
	private Random random = new Random();
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder passEnconder;
	@Autowired
	private EmailService emailService;
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null)
			throw new ObjectNotFoundException("Email não encontrado!");
		
		String newPass = newPassword();
		cliente.setSenha(passEnconder.encode(newPass));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[8];
		for(int i = 0; i < 8; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		if(opt == 0) {
			return (char) (random.nextInt(10) + 48);
		} else if(opt == 1) {
			return (char) (random.nextInt(26) + 65);
		} else {
			return (char) (random.nextInt(26) + 97);
		}
	}
}
