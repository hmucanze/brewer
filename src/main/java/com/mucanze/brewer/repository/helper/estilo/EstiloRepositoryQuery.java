package com.mucanze.brewer.repository.helper.estilo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mucanze.brewer.model.Estilo;
import com.mucanze.brewer.repository.filter.EstiloFilter;

public interface EstiloRepositoryQuery {
	
	public Page<Estilo> pesquisar(EstiloFilter filtro, Pageable pageable);

}
