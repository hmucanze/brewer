package com.mucanze.brewer.repository.filter;

import com.mucanze.brewer.model.Estado;

public class CidadeFilter {
	
	private String nome;
	private Estado estado;

	public CidadeFilter(String nome, Estado estado) {
		this.nome = nome;
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}
