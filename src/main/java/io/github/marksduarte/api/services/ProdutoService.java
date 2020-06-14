package io.github.marksduarte.api.services;

import java.util.List;
import java.util.Optional;

import io.github.marksduarte.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.github.marksduarte.api.domain.Categoria;
import io.github.marksduarte.api.domain.Pedido;
import io.github.marksduarte.api.domain.Produto;
import io.github.marksduarte.api.repositories.CategoriaRepository;
import io.github.marksduarte.api.repositories.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Produto find(final Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	/**
	 * Busca de pedidos por nome e categorias
	 * @param nome
	 * @param ids
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return
	 */
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage,  String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
