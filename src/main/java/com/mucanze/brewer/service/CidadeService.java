package com.mucanze.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.brewer.model.Cidade;
import com.mucanze.brewer.repository.CidadeRepository;
import com.mucanze.brewer.service.exception.NomeCidadeJaCadastradaException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Transactional
	public void salvar(Cidade cidade) {
		
		Optional<Cidade> cidadeRetornada = cidadeRepository.findByNomeIgnoreCase(cidade.getNome());
		if(cidadeRetornada.isPresent()) {
			throw new NomeCidadeJaCadastradaException("Nome da cidade já cadastrada");
		}
		
		cidadeRepository.save(cidade);
	}

}
