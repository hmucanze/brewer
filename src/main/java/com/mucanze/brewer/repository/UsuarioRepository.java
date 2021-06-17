package com.mucanze.brewer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mucanze.brewer.model.Usuario;
import com.mucanze.brewer.repository.helper.usuario.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmailIgnoreCase(String email);
	
	public List<Usuario> findByIdIn(Long[] usuariosId);
	
}
