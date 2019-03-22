package com.marksduarte.cursomc.dto;

import java.io.Serializable;

import com.marksduarte.cursomc.domain.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

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

	@NotBlank(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 80, message = "A quantidade de caracteres deve estar entre {min} e {max}.")
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
