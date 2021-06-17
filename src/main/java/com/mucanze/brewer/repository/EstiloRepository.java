package com.mucanze.brewer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mucanze.brewer.model.Estilo;
import com.mucanze.brewer.repository.helper.estilo.EstiloRepositoryQuery;

@Repository
public interface EstiloRepository extends JpaRepository<Estilo, Long>, EstiloRepositoryQuery {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

}
