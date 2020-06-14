package io.github.marksduarte.api.domain.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return this.cod;
	}
	
	public String getDescricao() {
		return this.descricao;
	}

	public static EstadoPagamento toEnum(Integer id) {
		if(id == null)
			return null;
		
		for(EstadoPagamento t : EstadoPagamento.values()) {
			if(id.equals(t.getCod()))
				return t;
		}
		
		throw new IllegalArgumentException("Id inválido: " +  id);
	}
}
