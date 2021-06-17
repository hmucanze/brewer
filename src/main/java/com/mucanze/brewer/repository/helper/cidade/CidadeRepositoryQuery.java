package com.mucanze.brewer.repository.helper.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mucanze.brewer.model.Cidade;
import com.mucanze.brewer.repository.filter.CidadeFilter;

public interface CidadeRepositoryQuery {
	
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);

}
