package com.mucanze.brewer.repository.filter;

public class ClienteFilter {
	
	private String nome;
	private String cpfOuCnpj;
		
	public ClienteFilter(String nome, String cpfOuCnpj) {
		this.nome = nome;
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

}
