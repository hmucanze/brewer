package com.mucanze.brewer.repository.helper.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.mucanze.brewer.model.Grupo;
import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.model.UsuarioGrupo;
import com.mucanze.brewer.repository.filter.UsuarioFilter;
import com.mucanze.brewer.repository.paginacao.PaginacaoUtil;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PaginacaoUtil PaginacaoUtil;
	
	@Override
	public Optional<Usuario> findByEmailAndActivo(String email) {
		return entityManager
				.createQuery("from Usuario where lower(email) = lower(:email) and activo = true", Usuario.class)
				.setParameter("email", email)
				.getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return entityManager
				.createQuery("select p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Page<Usuario> pesquisar(UsuarioFilter filtro, Pageable pageable) {
		
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		
		PaginacaoUtil.preparar(criteria, pageable);
		
		// criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		adicionarFiltro(filtro,criteria);
		
		List<Usuario> usuarios = criteria.list();
		usuarios.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		return new PageImpl<Usuario>(usuarios, pageable, total(filtro));
	}
	
	@SuppressWarnings("deprecation")
	private long total(UsuarioFilter filtro) {
		Criteria criteria = entityManager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		if(filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(),MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			// criteria.createAlias("grupos", "g", JoinType.LEFT_OUTER_JOIN);
			if(filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for(Long grupoId : filtro.getGrupos().stream().mapToLong(Grupo::getId).toArray()) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UsuarioGrupo.class);
					
					detachedCriteria.add(Restrictions.eq("id.grupo.id", grupoId));
					detachedCriteria.setProjection(Projections.property("id.usuario"));
					
					subqueries.add(Subqueries.propertyIn("id", detachedCriteria));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}


}
