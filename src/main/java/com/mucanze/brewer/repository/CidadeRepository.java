package com.mucanze.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mucanze.brewer.model.Cidade;
import com.mucanze.brewer.repository.helper.cidade.CidadeRepositoryQuery;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery {
	
	public Optional<Cidade> findByNomeIgnoreCase(String nome);
	
	public List<Cidade> findByEstadoId(Long estadoId);

}
