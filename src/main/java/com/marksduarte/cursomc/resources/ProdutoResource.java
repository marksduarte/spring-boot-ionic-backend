package com.marksduarte.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marksduarte.cursomc.domain.Produto;
import com.marksduarte.cursomc.dto.ProdutoDTO;
import com.marksduarte.cursomc.resources.utils.URL;
import com.marksduarte.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Produto produto = produtoService.find(id);
		return ResponseEntity.ok().body(produto);
	}
	
	/**
	 * Faz a paginação, buscando os dados aos poucos.
	 *
	 * @param page defaultValue is a page at 0
	 * @param linesPerPage defaultValue 24 [24 pois é divisivel por 1,2,3 e 4]
	 * @param orderBy defaultValue nome
	 * @param direction defaultValue ASC
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(name = "nome", defaultValue = "") String nome,
			@RequestParam(name = "categorias", defaultValue = "") String categorias,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction){
		/**
		 * O método decodeParam serve para decodificar a string enviada pela view.
		 * Exemplo: TV LED -> TV%20LED
		 */
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); 
		
		return ResponseEntity.ok().body(listDto);
	}

}
