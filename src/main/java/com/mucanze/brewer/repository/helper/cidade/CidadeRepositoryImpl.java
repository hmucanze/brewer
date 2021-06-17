package com.mucanze.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mucanze.brewer.model.Cidade;
import com.mucanze.brewer.repository.filter.CidadeFilter;
import com.mucanze.brewer.repository.paginacao.PaginacaoUtil;

public class CidadeRepositoryImpl implements CidadeRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Transactional
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cidade.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(cidadeFilter, criteria);
		criteria.createAlias("estado", "e", JoinType.LEFT_OUTER_JOIN);
		
		return new PageImpl<Cidade>(criteria.list(), pageable, total(cidadeFilter));
	}
	
	@SuppressWarnings("deprecation")
	private long total(CidadeFilter cidadeFilter) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Cidade.class);
		adicionarFiltro(cidadeFilter, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(CidadeFilter cidadeFilter, Criteria criteria) {
		if(cidadeFilter != null) {
			if(!StringUtils.isEmpty(cidadeFilter.getNome())) {
				criteria.add(Restrictions.ilike("nome", cidadeFilter.getNome(), MatchMode.ANYWHERE));
			}
			
			if(isEstadoPresente(cidadeFilter)) {
				criteria.add(Restrictions.eq("estado", cidadeFilter.getEstado()));
			}
			
		}
	}

	private boolean isEstadoPresente(CidadeFilter cidadeFilter) {
		return cidadeFilter.getEstado() != null && cidadeFilter.getEstado().getId() != null;
	}

}
