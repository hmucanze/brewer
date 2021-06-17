package com.mucanze.brewer.repository.helper.cerveja;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mucanze.brewer.model.Cerveja;
import com.mucanze.brewer.repository.filter.CervejaFilter;

public interface CervejaRepositoryQuery {
	
	public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable);

}
