package com.mucanze.brewer.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {
	
	public void preparar(Criteria criteria, Pageable pageable) {
		int paginaActual = pageable.getPageNumber();
		int totalRegistoPorPagina = pageable.getPageSize();
		int primeiroRegisto = paginaActual * totalRegistoPorPagina;
		
		criteria.setFirstResult(primeiroRegisto);
		criteria.setMaxResults(totalRegistoPorPagina);
		
		Sort sort = pageable.getSort();
		if(sort.isSorted()) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
		}
	}

}
