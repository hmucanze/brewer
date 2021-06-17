package com.mucanze.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.brewer.model.Estilo;
import com.mucanze.brewer.repository.EstiloRepository;
import com.mucanze.brewer.service.exception.NomeEstiloCadastradoException;

@Service
public class EstiloService {
	
	@Autowired
	private EstiloRepository estiloRepository;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		Optional<Estilo> estiloRetornado = estiloRepository.findByNomeIgnoreCase(estilo.getNome());
		if(estiloRetornado.isPresent()) {
			throw new NomeEstiloCadastradoException("Nome do estilo já cadastrado!");
		}
		return estiloRepository.saveAndFlush(estilo);
	}

}
