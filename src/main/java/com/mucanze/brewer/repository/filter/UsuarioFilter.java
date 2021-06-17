package com.mucanze.brewer.repository.filter;

import java.util.List;

import com.mucanze.brewer.model.Grupo;

public class UsuarioFilter { 
	
	private String nome;
	private String email;
	private List<Grupo> grupos;
	
	public UsuarioFilter(String nome, String email, List<Grupo> grupos) {
		super();
		this.nome = nome;
		this.email = email;
		this.grupos = grupos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

}
