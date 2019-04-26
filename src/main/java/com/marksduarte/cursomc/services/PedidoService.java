package com.marksduarte.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.Pedido;
import com.marksduarte.cursomc.repositories.PedidoRepository;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(final Integer id) {
		Optional<Pedido> pedido = repo.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

}
