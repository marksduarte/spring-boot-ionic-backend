package com.marksduarte.cursomc.domain.enums;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public static TipoCliente toEnum(Integer id) {
		if(id == null)
			return null;
		
		for(TipoCliente t : TipoCliente.values()) {
			if(id.equals(t.getCod()))
				return t;
		}
		
		throw new IllegalArgumentException("Id inválido: " +  id);
	}
}
