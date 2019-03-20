package com.marksduarte.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marksduarte.cursomc.domain.Categoria;
import com.marksduarte.cursomc.repositories.CategoriaRepository;
import com.marksduarte.cursomc.services.exceptions.ObjectNotFoundException;



@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(final Integer id) {
		Optional<Categoria> categoria = repo.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public void update(Categoria obj) {
		find(obj.getId()); //-> Chamar o método find para verificar se o objeto existe no banco.
		repo.save(obj);
	}
}
