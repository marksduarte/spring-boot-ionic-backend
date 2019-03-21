package com.marksduarte.cursomc.dto;

import java.io.Serializable;

import com.marksduarte.cursomc.domain.Categoria;

/**
 * Uma classe DTO serve para quando queremos enviar somente alguns
 * atributos na requisição.
 * 
 * @author marks
 *
 */
public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}

	public CategoriaDTO(Categoria obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
