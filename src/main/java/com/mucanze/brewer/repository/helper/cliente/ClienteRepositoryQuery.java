package com.mucanze.brewer.repository.helper.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mucanze.brewer.model.Cliente;
import com.mucanze.brewer.repository.filter.ClienteFilter;

public interface ClienteRepositoryQuery {
	
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);

}
