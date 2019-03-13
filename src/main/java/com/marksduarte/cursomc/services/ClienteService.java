package com.marksduarte.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.Cliente;
import com.marksduarte.cursomc.repositories.ClienteRepository;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(final Integer id) {
		Optional<Cliente> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
}
