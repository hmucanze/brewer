package com.mucanze.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mucanze.brewer.model.Estilo;
import com.mucanze.brewer.repository.filter.EstiloFilter;
import com.mucanze.brewer.repository.paginacao.PaginacaoUtil;

public class EstiloRepositoryImpl implements EstiloRepositoryQuery {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	@Transactional
	public Page<Estilo> pesquisar(EstiloFilter filtro, Pageable pageable) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro,criteria);
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@SuppressWarnings("deprecation")
	private long total(EstiloFilter filtro) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Estilo.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EstiloFilter filtro, Criteria criteria) {
		if(!StringUtils.isEmpty(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
	}

}
