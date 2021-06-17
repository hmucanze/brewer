package com.mucanze.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.brewer.model.Cliente;
import com.mucanze.brewer.repository.ClienteRepository;
import com.mucanze.brewer.service.exception.CpfOuCnpjClienteJaCadastradoException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Transactional
	public void salvar(Cliente cliente) {
		Optional<Cliente> clienteExistente = clienteRepository.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
		if(clienteExistente.isPresent()) {
			throw new CpfOuCnpjClienteJaCadastradoException("CPF/CNPJ do cliente já cadastrado");
		}
		clienteRepository.save(cliente);
	}

}
